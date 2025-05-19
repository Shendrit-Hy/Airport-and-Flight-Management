import React, { useState, createContext, useContext, useEffect } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/BookingPage.css";

const BookingContext = createContext();

function BookingProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null);
  return (
    <BookingContext.Provider value={{ userInfo, setUserInfo }}>
      {children}
    </BookingContext.Provider>
  );
}

function BookingDetails({ flight }) {
  return (
    <div className="bookingpage-details">
      <h2>Flight Details</h2>
      <p className="bookingpage-detail-box">
        {new Date(flight.departureTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
        {" → "}
        {new Date(flight.arrivalTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
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
        age: Yup.number().min(1).required("Required"),
        phone: Yup.string().required("Required"),
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

  useEffect(() => {
    const tenantId = localStorage.getItem('tenantId') || 'default-tenant';
    
    axios.get(`http://localhost:8080/api/seats/available/${flight.id}`, {
      headers: {
        'X-Tenant-ID': tenantId
      }
    })
    .then((res) => {
      console.log('Available seats response:', res.data);
      if (Array.isArray(res.data)) {
        // Filter out seats with null/undefined IDs
        const validSeats = res.data.filter(seat => seat && seat.id != null);
        setAvailableSeats(validSeats);
      } else {
        console.error('Invalid seats data format:', res.data);
        setAvailableSeats([]);
      }
    })
    .catch((error) => {
      console.error('Error fetching available seats:', error);
      setAvailableSeats([]);
    });
  }, [flight.id]);

  const toggleSeat = (seat) => {
    const seatId = seat.id;
    const isSelected = selectedSeats.some(s => s.id === seatId);
    
    if (isSelected) {
      setSelectedSeats(selectedSeats.filter(s => s.id !== seatId));
    } else if (selectedSeats.length < userInfo.ticketCount) {
      setSelectedSeats([...selectedSeats, seat]);
    }
  };

  const isSeatSelected = (seat) => {
    return selectedSeats.some(s => s.id === seat.id);
  };

  return (
    <Formik
      initialValues={{ cardNumber: "", cvc: "" }}
      validationSchema={Yup.object({
        cardNumber: Yup.string().required("Required"),
        cvc: Yup.string().required("Required"),
      })}
      onSubmit={async () => {
        try {
          const tenantId = localStorage.getItem('tenantId') || 'default-tenant';
          
          await axios.post("/api/bookings", {
            ...userInfo,
            flightId: flight.id,
            seats: selectedSeats.map(seat => seat.id),
            totalPrice: flight.price * userInfo.ticketCount,
          }, {
            headers: {
              'X-Tenant-ID': tenantId
            }
          });
          navigate("/checkin");
        } catch (error) {
          console.error('Booking failed:', error);
          alert("Booking failed. Please try again.");
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
                key={seat.id}  // Now guaranteed to be non-null
                type="button"
                className={`bookingpage-seat ${isSeatSelected(seat) ? "selected" : ""}`}
                onClick={() => toggleSeat(seat)}
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