import axios from 'axios';

const API_URL = "http://localhost:8080/api/flights";

export const FrontendRepo = {
    getFlights: () => axios.get(API_URL),
    bookFlight: (flightId, userData) => axios.post(`${API_URL}/book/${flightId}`, userData),
    cancelBooking: (bookingId) => axios.delete(`${API_URL}/cancel/${bookingId}`)
};
