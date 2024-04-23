import React, { useState } from 'react';
import './App.css'; // Import CSS file for styling

function App() {
    const [venue, setVenue] = useState('');
    const [ticketPrices, setTicketPrices] = useState([]);
    const [bookmarked, setBookmarked] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [showLoginScreen, setLoginScreen] = useState(true);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showBookmarkedTickets, setBookmarkedTickets] = useState(false);

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/addUser',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
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
        //console.log(ticket);
        ticket.email = email;
        try {
            const response = await fetch('http://localhost:8080/addUserTicket', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(ticket),
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

    const getBookmark = async () => {
        try {
            const response = await fetch('http://localhost:8080/userTickets/' + email, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const data = await response.json();
                //console.log(data);
                setBookmarked(data);
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
            <button onClick={() => {setBookmarkedTickets(true); getBookmark()}}>Show Bookmarked Tickets</button>
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
                        <label htmlFor="email">   Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                        <br></br>
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                        <br></br>
                        <button type="submit">Login</button>
                    </form>
                </div>
            )}
            {showBookmarkedTickets && (
                <div className="login-screen">
                <button onClick={() => setBookmarkedTickets(false)}>Back</button>
                <h2>Ticket Prices</h2>
                <ul>
                    {bookmarked.map((ticket, index) => (
                        <li key={index}>
                            <strong>{ticket.venue + "\t" + ticket.artist + "\t" + ticket.city_state + "\t" + ticket.date + "\t" + ticket.time + "\t" + ticket.seat_number }:</strong> ${ticket.price}
                        </li>
                    ))}
                </ul>
            </div>
            )}
        </div>
    );
}

export default App;