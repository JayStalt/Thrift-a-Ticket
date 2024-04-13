import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')

import React from 'react';
import ReactDOM from 'react-dom';
import './index.css'; // Import other CSS files if needed
import App from './App'; // Import the React component

ReactDOM.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
    document.getElementById('root')
);

import React, { useState } from 'react';
import './App.css'; // Import CSS file for styling

function App() {
    const [venue, setVenue] = useState('');
    const [ticketPrices, setTicketPrices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSearch = async () => {
        setError('');
        setLoading(true);
        try {
            // Mock fetch for demonstration purposes
            const response = await fetch(`https://api.example.com/tickets?venue=${venue}`);
            const data = await response.json();
            setTicketPrices(data.prices);
        } catch (error) {
            setError('Failed to fetch ticket prices. Please try again later.');
        }
        setLoading(false);
    };

    return (
        <div className="container">
            <h1>Concert Ticket Search</h1>
            <div className="search-box">
                <input
                    type="text"
                    placeholder="Enter Venue..."
                    value={venue}
                    onChange={(e) => setVenue(e.target.value)}
                />
                <button onClick={handleSearch} disabled={loading}>
                    {loading ? 'Searching...' : 'Search'}
                </button>
            </div>
            {error && <div className="error">{error}</div>}
            {ticketPrices.length > 0 && (
                <div className="ticket-prices">
                    <h2>Ticket Prices</h2>
                    <ul>
                        {ticketPrices.map((ticket, index) => (
                            <li key={index}>
                                <strong>{ticket.type}:</strong> ${ticket.price}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

export default App;
