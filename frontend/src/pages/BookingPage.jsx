import React, { useState, createContext, useContext } from "react";
import { Formik, Form, Field } from "formik";
import "../styles/BookingPage.css";
import { useNavigate } from "react-router-dom";

const BookingContext = createContext();

function BookingProvider({ children }) {
  const [userInfo, setUserInfo] = useState(null);
  return (
    <BookingContext.Provider value={{ userInfo, setUserInfo }}>
      {children}
    </BookingContext.Provider>
  );
}

function Navbar() {
  return (
    <div className="navbar">
      <div className="navbar-left">Flight Booking</div>
      <div className="navbar-links">
        <a href="#">Home</a>
        <a href="#">Contact</a>
        <a href="#">Flights</a>
      </div>
    </div>
  );
}

function BookingDetails({ flightDetails }) {
  return (
    <div className="booking-details">
      <h2>Details</h2>
      <p className="detail-box">{flightDetails.time}</p>
      <p className="detail-box">FROM: {flightDetails.from}</p>
      <p className="detail-box">TO: {flightDetails.to}</p>
      <p className="price">{flightDetails.price}</p>
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
      onSubmit={(values) => {
        setUserInfo({ ...values, ticketCount });
        next();
      }}
    >
      <Form className="form">
        <div className="tab-bar">
          <span className="tab active">Your info</span>
          <span className="tab">Payment</span>
        </div>

        <Field name="fullName" placeholder="Full Name" className="input" />
        <Field name="email" type="email" placeholder="Email" className="input" />
        <Field name="age" placeholder="Age" className="input" />
        <Field name="phone" placeholder="Phone Number" className="input" />

        <div className="ticket-control">
          <span>Add another ticket:</span>
          <div className="ticket-buttons">
            <button type="button" onClick={decrease}>−</button>
            <span className="ticket-count">{ticketCount}</span>
            <button type="button" onClick={increase}>+</button>
          </div>
        </div>

        <button type="submit" className="button">Continue to payment</button>
      </Form>
    </Formik>
  );
}


function PaymentForm({ flightDetails }) {
  const { userInfo } = useContext(BookingContext);
  const navigate = useNavigate();

  return (
    <Formik
      initialValues={{ cardNumber: "", cvc: "" }}
      onSubmit={() => {
        console.log("Booking info:", userInfo);
        navigate("/checkin");
      }}
    >
      <Form className="form">
        <div className="tab-bar">
          <span className="tab">Your info</span>
          <span className="tab active">Payment</span>
        </div>
        <Field name="cardNumber" placeholder="Card Number" className="input" />
        <Field name="cvc" placeholder="CVC" className="input" />
        <p className="price">Total: {flightDetails.price}</p>
        <button type="submit" className="button">Buy</button>
      </Form>
    </Formik>
  );
}

function BookingPage() {
  const [step, setStep] = useState(0);

  const flightDetails = {
    time: "20:35PRN→08:55CDG",
    from: "Pristina, Pristina International - Kosovo",
    to: "Istanbul, Istanbul - Turkey",
    price: "€907.04",
  };

  return (
    <BookingProvider>
      <Navbar />
      <div className="booking-page-container">
        <h1 className="booking-title">Booking Page</h1>
        <div className="content">
          <BookingDetails flightDetails={flightDetails} />
          {step === 0 ? (
            <UserInfoForm next={() => setStep(1)} />
          ) : (
            <PaymentForm flightDetails={flightDetails} />
          )}
        </div>
      </div>
    </BookingProvider>
  );
}

export default BookingPage;
