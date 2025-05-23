import React, { useState, createContext, useContext, useEffect } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useLocation, useNavigate } from "react-router-dom";
import "../styles/BookingPage.css";
import { getUserProfile } from "../utils/auth";
import { createBooking } from "../api/bookingService";
import { createPassenger } from "../api/passengerService";
import { savePayment } from "../api/paymentService";
import { getAvailableSeats, markSeatAsUnavailable } from "../api/seatService";

const BookingContext = createContext();

function BookingProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null);
  return (
    <BookingContext.Provider value={{ userInfo, setUserInfo }}>
      {children}
    </BookingContext.Provider>
  );
}

function parseTimeString(timeStr) {
  const [hours, minutes, seconds] = timeStr.split(":").map(Number);
  const now = new Date();
  now.setHours(hours, minutes, seconds || 0, 0);
  return now;
}

function BookingDetails({ flight }) {
  const departureTime = parseTimeString(flight.departureTime);
  const arrivalTime = parseTimeString(flight.arrivalTime);
  return (
    <div className="bookingpage-details">
      <h2>Flight Details</h2>
      <p className="bookingpage-detail-box">
        {new Date(departureTime).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })} → {new Date(arrivalTime).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}
      </p>
      <p className="bookingpage-detail-box">From: {flight.departureAirport}</p>
      <p className="bookingpage-detail-box">To: {flight.arrivalAirport}</p>
      <p className="bookingpage-price">€{flight.price}</p>
    </div>
  );
}

function UserInfoForm({ next }) {
  const { setUserInfo } = useContext(BookingContext);
  const [ticketCount, setTicketCount] = useState(1);

  const increase = () => setTicketCount(ticketCount + 1);
  const decrease = () => setTicketCount(ticketCount > 1 ? ticketCount - 1 : 1);

  return (
    <Formik
      initialValues={{ fullName: "", email: "", age: "", phone: "" }}
      validationSchema={Yup.object({
        fullName: Yup.string().required("Required"),
        email: Yup.string().email("Invalid email").required("Required"),
        age: Yup.number().required("Required").moreThan(18, "You must be older than 18"),
        phone: Yup.string()
          .required("Required")
          .matches(/^\+383\d{8}$/, "Phone must start with +383 and contain 8 digits after"),
      })}
      onSubmit={(values) => {
        setUserInfo({ ...values, ticketCount });
        next();
      }}
    >
      <Form className="bookingpage-form">
        <div className="bookingpage-tab-bar">
          <span className="bookingpage-tab active">Your Info</span>
          <span className="bookingpage-tab">Payment</span>
        </div>

        <Field name="fullName" placeholder="Full Name" className="bookingpage-input" />
        <ErrorMessage name="fullName" component="div" className="bookingpage-error" />
        <Field name="email" type="email" placeholder="Email" className="bookingpage-input" />
        <ErrorMessage name="email" component="div" className="bookingpage-error" />
        <Field name="age" placeholder="Age" className="bookingpage-input" />
        <ErrorMessage name="age" component="div" className="bookingpage-error" />
        <Field name="phone" placeholder="Phone Number" className="bookingpage-input" />
        <ErrorMessage name="phone" component="div" className="bookingpage-error" />

        <div className="bookingpage-ticket-control">
          <span>Tickets:</span>
          <div className="bookingpage-ticket-buttons">
            <button type="button" onClick={decrease}>−</button>
            <span className="bookingpage-ticket-count">{ticketCount}</span>
            <button type="button" onClick={increase}>+</button>
          </div>
        </div>

        <button type="submit" className="bookingpage-button">Continue to Payment</button>
      </Form>
    </Formik>
  );
}

function PaymentForm({ flight }) {
  const { userInfo } = useContext(BookingContext);
  const [availableSeats, setAvailableSeats] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const navigate = useNavigate();
  const tenantId = flight.tenantId;

  useEffect(() => {
    getAvailableSeats(flight.id, tenantId)
      .then((res) => {
        const seats = Array.isArray(res.data) ? res.data : [];
        setAvailableSeats(seats);
      })
      .catch(() => setAvailableSeats([]));
  }, [flight.id, tenantId]);

  const toggleSeatSelection = (seat, selected, max) => {
    const exists = selected.find(s => s.id === seat.id);
    if (exists) return selected.filter(s => s.id !== seat.id);
    if (selected.length < max) return [...selected, seat];
    return selected;
  };

  const isSeatSelectedHelper = (seat, selected) =>
    selected.some(s => s.id === seat.id);

  const handleSeatClick = (seat) => {
    setSelectedSeats(toggleSeatSelection(seat, selectedSeats, userInfo.ticketCount));
  };

  return (
    <Formik
      initialValues={{ cardNumber: "", cvc: "" }}
      validationSchema={Yup.object({
        cardNumber: Yup.string()
          .required("Required")
          .matches(/^(\d{4}[\s\/-]?){3}\d{4}$/, "Card number must be 16 digits with optional space, /, or -")
          .test("luhn-check", "Invalid card number", value => {
            if (!value) return false;
            const sanitized = value.replace(/[^\d]/g, "");
            let sum = 0;
            let shouldDouble = false;
            for (let i = sanitized.length - 1; i >= 0; i--) {
              let digit = parseInt(sanitized[i]);
              if (shouldDouble) {
                digit *= 2;
                if (digit > 9) digit -= 9;
              }
              sum += digit;
              shouldDouble = !shouldDouble;
            }
            return sum % 10 === 0;
          }),
        cvc: Yup.string()
          .required("Required")
          .matches(/^\d{3}$/, "CVC must be exactly 3 digits"),
      })}
      onSubmit={async () => {
        try {
          const token = localStorage.getItem("token");
          const userProfile = await getUserProfile(token);

          const passengerPromises = Array(userInfo.ticketCount).fill().map(() =>
            createPassenger({
              fullName: userInfo.fullName,
              email: userInfo.email,
              age: userInfo.age,
              phone: userInfo.phone,
            }, tenantId)
          );

          const passengerResponses = await Promise.all(passengerPromises);
          const bookingResponse = await createBooking({
            flightNumber: flight.id,
            seatNumber: selectedSeats.map(seat => seat.seatNumber).join(','),
            passengerId: userProfile.data.id,
            passengerName: passengerResponses[0].data.fullName,
          }, token);

          await savePayment({
            bookingId: bookingResponse.data.id,
            amount: flight.price * userInfo.ticketCount,
            method: "CARD",
            reference: `BOOK-${bookingResponse.data.id}`
          }, token, tenantId);

          await Promise.all(selectedSeats.map(seat =>
            markSeatAsUnavailable(seat.id, token)
          ));

          navigate("/profile");
        } catch (err) {
          alert("Booking failed. Please try again.");
          console.error(err);
        }
      }}
    >
      <Form className="bookingpage-form">
        <div className="bookingpage-tab-bar">
          <span className="bookingpage-tab">Your Info</span>
          <span className="bookingpage-tab active">Payment</span>
        </div>

        <Field name="cardNumber" placeholder="Card Number" className="bookingpage-input" />
        <ErrorMessage name="cardNumber" component="div" className="bookingpage-error" />

        <Field name="cvc" placeholder="CVC" className="bookingpage-input" />
        <ErrorMessage name="cvc" component="div" className="bookingpage-error" />

        <div className="bookingpage-seats">
          <h3>Select {userInfo.ticketCount} Seat(s):</h3>
          <div className="bookingpage-seat-grid">
            {availableSeats.map(seat => (
              <button
                key={seat.id}
                type="button"
                className={`bookingpage-seat ${isSeatSelectedHelper(seat, selectedSeats) ? "selected" : ""}`}
                onClick={() => handleSeatClick(seat)}
              >
                {seat.seatNumber || `${seat.seatRow}${seat.seatColumn}` || seat.id}
              </button>
            ))}
          </div>
          {availableSeats.length === 0 && (
            <p className="bookingpage-no-seats">No available seats found.</p>
          )}
        </div>

        {selectedSeats.length !== userInfo.ticketCount && (
          <div className="bookingpage-error">Please select exactly {userInfo.ticketCount} seats.</div>
        )}

        <p className="bookingpage-price">
          Total: €{(flight.price * userInfo.ticketCount).toFixed(2)}
        </p>

        <button
          type="submit"
          className="bookingpage-button"
          disabled={selectedSeats.length !== userInfo.ticketCount}
        >
          Buy
        </button>
      </Form>
    </Formik>
  );
}

function Book() {
  const { state } = useLocation();
  const [step, setStep] = useState(0);
  const flight = state?.flight;

  if (!flight) {
    return <p>No flight selected. Please go back and choose a flight.</p>;
  }

  return (
    <BookingProvider>
      <div className="bookingpage-container">
        <h1 className="bookingpage-title">Book Your Flight</h1>
        <div className="bookingpage-content">
          <BookingDetails flight={flight} />
          {step === 0 ? (
            <UserInfoForm next={() => setStep(1)} />
          ) : (
            <PaymentForm flight={flight} />
          )}
        </div>
      </div>
    </BookingProvider>
  );
}

export default Book;
