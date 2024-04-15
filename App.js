import React, { useState } from 'react';
import './App.css'; // Import CSS file for styling

function App() {
    const [venue, setVenue] = useState('');
    const [ticketPrices, setTicketPrices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [showLoginScreen, setLoginScreen] = useState(true);
    const [email, setEmail] = useState('');

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/addUser?email=' + email, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email }),
            });

            if (response.ok) {
                const data = await response;
                console.log(data);
                // Handle successful response from the API
            } else {
                throw new Error('Failed to post data');
            }
        } catch (error) {
            console.error('Error:', error);
            // Handle errors from the fetch request
        }
        setLoginScreen(false);
    };

    const handleSearch = async () => {
        setError('');
        setLoading(true);
        try {
            const response = await fetch(`http://localhost:8080/allUserTickets`, {
                method: 'GET',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            const data = await response.json();
            console.log(data);
            setTicketPrices(data);
        } catch (error) {
            setError('Failed to fetch ticket prices. Please try again later.');
        }
        setLoading(false);
    };

    const bookmark = async (ticket) => {
        try {
            const response = await fetch('http://localhost:8080/addUserTicket', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ticket }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log(data);
                // Handle successful response from the API
            } else {
                throw new Error('Failed to post data');
            }
        } catch (error) {
            console.error('Error:', error);
            // Handle errors from the fetch request
        }
    }

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
                                <strong>{ticket.venue + "\t" + ticket.artist + "\t" + ticket.city_state + "\t" + ticket.date + "\t" + ticket.time + "\t" + ticket.seat_number }:</strong> ${ticket.price}
                                <br></br><button onClick={() => bookmark(ticket)}>Bookmark</button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {showLoginScreen && (
                <div className='login-screen'>
                    <h2>Login</h2>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                        <button type="submit">Login</button>
                    </form>
                </div>
            )}
        </div>
    );
}

export default App;