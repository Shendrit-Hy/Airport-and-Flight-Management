import React, { useState, createContext, useContext, useEffect } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/BookingPage.css";
<<<<<<< Updated upstream
=======
import {
  fetchAvailableSeats,
  submitBooking,
  toggleSeatSelection,
  isSeatSelectedHelper,
  createPassenger,
  createBooking
} from "../api/bookedService";
import { getUserProfile } from "../utils/auth";
import { useLanguage } from "../context/LanguageContext"; // ✅ për përkthim
>>>>>>> Stashed changes

const BookingContext = createContext();

function BookingProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null);
  return (
    <BookingContext.Provider value={{ userInfo, setUserInfo }}>
      {children}
    </BookingContext.Provider>
  );
}

<<<<<<< Updated upstream
function BookingDetails({ flight }) {
=======
function parseTimeString(timeStr) {
  const [hours, minutes, seconds] = timeStr.split(':').map(Number);
  const now = new Date();
  now.setHours(hours, minutes, seconds || 0, 0);
  return now;
}

function BookingDetails({ flight }) {
  const { t } = useLanguage(); // ✅
  const departureTime = parseTimeString(flight.departureTime);
  const arrivalTime = parseTimeString(flight.arrivalTime);
>>>>>>> Stashed changes
  return (
    <div className="bookingpage-details">
      <h2>{t("Flight Details", "Detajet e Fluturimit")}</h2>
      <p className="bookingpage-detail-box">
        {new Date(flight.departureTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
        {" → "}
        {new Date(flight.arrivalTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
      </p>
      <p className="bookingpage-detail-box">{t("From", "Prej")}: {flight.departureAirport}</p>
      <p className="bookingpage-detail-box">{t("To", "Deri")}: {flight.arrivalAirport}</p>
      <p className="bookingpage-price">€{flight.price}</p>
    </div>
  );
}

function UserInfoForm({ next }) {
  const { t } = useLanguage(); // ✅
  const { setUserInfo } = useContext(BookingContext);
  const [ticketCount, setTicketCount] = useState(1);

  const increase = () => setTicketCount(ticketCount + 1);
  const decrease = () => setTicketCount(ticketCount > 1 ? ticketCount - 1 : 1);

  return (
    <Formik
      initialValues={{ fullName: "", email: "", age: "", phone: "" }}
      validationSchema={Yup.object({
        fullName: Yup.string().required(t("Required", "E detyrueshme")),
        email: Yup.string().email(t("Invalid email", "Email i pavlefshëm")).required(t("Required", "E detyrueshme")),
        age: Yup.number().min(1).required(t("Required", "E detyrueshme")),
        phone: Yup.string().required(t("Required", "E detyrueshme")),
      })}
      onSubmit={(values) => {
        setUserInfo({ ...values, ticketCount });
        next();
      }}
    >
      <Form className="bookingpage-form">
        <div className="bookingpage-tab-bar">
          <span className="bookingpage-tab active">{t("Your Info", "Të dhënat e tua")}</span>
          <span className="bookingpage-tab">{t("Payment", "Pagesa")}</span>
        </div>

        <Field name="fullName" placeholder={t("Full Name", "Emri i plotë")} className="bookingpage-input" />
        <ErrorMessage name="fullName" component="div" className="bookingpage-error" />

        <Field name="email" type="email" placeholder={t("Email", "Email")} className="bookingpage-input" />
        <ErrorMessage name="email" component="div" className="bookingpage-error" />

        <Field name="age" placeholder={t("Age", "Mosha")} className="bookingpage-input" />
        <ErrorMessage name="age" component="div" className="bookingpage-error" />

        <Field name="phone" placeholder={t("Phone Number", "Numri i telefonit")} className="bookingpage-input" />
        <ErrorMessage name="phone" component="div" className="bookingpage-error" />

        <div className="bookingpage-ticket-control">
          <span>{t("Tickets", "Bileta")}:</span>
          <div className="bookingpage-ticket-buttons">
            <button type="button" onClick={decrease}>−</button>
            <span className="bookingpage-ticket-count">{ticketCount}</span>
            <button type="button" onClick={increase}>+</button>
          </div>
        </div>

        <button type="submit" className="bookingpage-button">
          {t("Continue to Payment", "Vazhdo në Pagesë")}
        </button>
      </Form>
    </Formik>
  );
}

function PaymentForm({ flight }) {
  const { t } = useLanguage(); // ✅
  const { userInfo } = useContext(BookingContext);
  const [availableSeats, setAvailableSeats] = useState([]);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
<<<<<<< Updated upstream
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
=======
    fetchAvailableSeats(flight.id)
      .then((seats) => setAvailableSeats(seats))
      .catch((error) => {
        console.error("Failed to fetch seats:", error);
        setAvailableSeats([]);
      });
  }, [flight.id]);
>>>>>>> Stashed changes

  const isSeatSelected = (seat) => {
    return selectedSeats.some(s => s.id === seat.id);
  };

  return (
    <Formik
      initialValues={{ cardNumber: "", cvc: "" }}
      validationSchema={Yup.object({
        cardNumber: Yup.string().required(t("Required", "E detyrueshme")),
        cvc: Yup.string().required(t("Required", "E detyrueshme")),
      })}
      onSubmit={async () => {
        try {
<<<<<<< Updated upstream
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
=======
          const userProfile = await getUserProfile(localStorage.getItem("token"));
          await createBooking(userProfile, userInfo, selectedSeats, flight);
          await createPassenger({
            fullName: userInfo.fullName,
            email: userInfo.email,
            age: userInfo.age,
            phone: userInfo.phone
          });
          navigate("/checkin");
        } catch (err) {
          alert(t("Booking failed. Please try again.", "Rezervimi dështoi. Ju lutem provoni përsëri."));
          console.error(err);
>>>>>>> Stashed changes
        }
      }}
    >
      <Form className="bookingpage-form">
        <div className="bookingpage-tab-bar">
          <span className="bookingpage-tab">{t("Your Info", "Të dhënat e tua")}</span>
          <span className="bookingpage-tab active">{t("Payment", "Pagesa")}</span>
        </div>

        <Field name="cardNumber" placeholder={t("Card Number", "Numri i Kartelës")} className="bookingpage-input" />
        <ErrorMessage name="cardNumber" component="div" className="bookingpage-error" />

        <Field name="cvc" placeholder={t("CVC", "CVC")} className="bookingpage-input" />
        <ErrorMessage name="cvc" component="div" className="bookingpage-error" />

        <div className="bookingpage-seats">
          <h3>{t("Select", "Zgjidh")} {userInfo.ticketCount} {t("Seat(s)", "ulëse(t)")}:</h3>
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
            <p className="bookingpage-no-seats">
              {t("No available seats found.", "Nuk u gjetën ulëse të lira.")}
            </p>
          )}
        </div>

        {selectedSeats.length !== userInfo.ticketCount && (
          <div className="bookingpage-error">
            {t("Please select exactly", "Ju lutem zgjidhni saktësisht")} {userInfo.ticketCount} {t("seats", "ulëse")}.
          </div>
        )}

        <p className="bookingpage-price">
          {t("Total", "Totali")}: €{(flight.price * userInfo.ticketCount).toFixed(2)}
        </p>

        <button
          type="submit"
          className="bookingpage-button"
          disabled={selectedSeats.length !== userInfo.ticketCount}
        >
          {t("Buy", "Bli")}
        </button>
      </Form>
    </Formik>
  );
}

function Book() {
  const { t } = useLanguage(); // ✅
  const { state } = useLocation();
  const [step, setStep] = useState(0);
  const flight = state?.flight;

  if (!flight) {
    return (
      <p>
        {t("No flight selected. Please go back and choose a flight.", "Asnjë fluturim nuk është zgjedhur. Kthehuni dhe zgjidhni një fluturim.")}
      </p>
    );
  }

  return (
    <BookingProvider>
      <div className="bookingpage-container">
        <h1 className="bookingpage-title">{t("Book Your Flight", "Rezervo Fluturimin")}</h1>
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