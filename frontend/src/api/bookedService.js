import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
const tenantId = getTenantIdFromSubdomain();

export const fetchAvailableSeats = async (flightId) => {
  const res = await axios.get(`http://localhost:8080/api/seats/available/${flightId}`, {
    headers: { 'X-Tenant-ID': tenantId }
  });

  const validSeats = Array.isArray(res.data)
    ? res.data.filter(seat => seat && seat.id != null)
    : [];
    console.log(res)
    console.log(res.data)
    console.log(validSeats)
  return validSeats;
};

export const submitBooking = async (userInfo, flight, selectedSeats) => {
  await axios.post("http://localhost:8080/api/bookings", {
    ...userInfo,
    flightId: flight.id,
    seats: selectedSeats.map(seat => seat.id),
    totalPrice: flight.price * userInfo.ticketCount,
  }, {
    headers: { 'X-Tenant-ID': tenantId }
  });
};

export const toggleSeatSelection = (seat, selectedSeats, maxSeats) => {
  const isSelected = selectedSeats.some(s => s.id === seat.id);
  if (isSelected) {
    return selectedSeats.filter(s => s.id !== seat.id);
  } else if (selectedSeats.length < maxSeats) {
    return [...selectedSeats, seat];
  }
  return selectedSeats;
};

export const createBooking = async (userProfile, userInfo, selectedSeats, flight) => {
  // Join multiple seat numbers into a single string (e.g. "12A,12B,12C")
  const seatNumbers = selectedSeats.map(seat => seat.seat_number).join(',');
  await axios.post("http://localhost:8080/api/bookings", {
    flight_number: flight.flightNumber,
    passenger_name: userInfo.fullName,
    seat_number: seatNumbers,
    passenger_id: userProfile.data.id
  }, {
    headers: {
      'X-Tenant-ID': tenantId
    }
  });
};

export const createPassenger = async (passengerData) => {
  try {
    const response = await fetch('http://localhost:8080/api/passengers', {
      method: 'POST',
      headers: {
        'X-Tenant-ID': tenantId ,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(passengerData)
    });
    
    if (!response.ok) {
      throw new Error('Failed to create passenger');
    }
    
    return await response.json();
  } catch (error) {
    console.error('Error creating passenger:', error);
    throw error;
  }
};

export const isSeatSelectedHelper = (seat, selectedSeats) =>
  selectedSeats.some(s => s.id === seat.id);
