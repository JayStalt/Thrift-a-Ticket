import React, { useState } from 'react';
import './App.css'; // Import CSS file for styling

function App() {
    const [venue, setVenue] = useState('');
    const [ticketPrices, setTicketPrices] = useState([]);
    const [bookmarked, setBookmarked] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [showLoginScreen, setLoginScreen] = useState(true);
    const [showRegisterScreen, setRegistrationScreen] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showBookmarkedTickets, setBookmarkedTickets] = useState(false);
    const [showEmailInUseError, setEmailInUseError] = useState(false);
    const [showLoginError, setLoginError] = useState(false);

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const showRegisterPage = () => {
        setLoginScreen(false);
        setRegistrationScreen(true);
    }

    const showLoginPage = () => {
        setLoginScreen(true);
        setRegistrationScreen(false);
    }

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/login',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
            });

            if (response.ok) {
                const data = await response.json();
                if(data === true) {
                    setLoginScreen(false);
                    setPassword('null');
                } else {
                    setLoginError(true);
                }
            } else {
                throw new Error('Failed to post data');
            }
        } catch (error) {
            console.error('Error:', error);
            // Handle errors from the fetch request
        }
    };

    const handleRegistration= async (e) => {
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
                const data = await response.json();
                if(data === 2) {
                    setRegistrationScreen(false);
                    setPassword('null');
                } else if(data === 0) {
                    setEmailInUseError(true);
                }
            } else {
                throw new Error('Failed to post data');
            }
        } catch (error) {
            console.error('Error:', error);
            // Handle errors from the fetch request
        }
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
            setTicketPrices(data);
        } catch (error) {
            setError('Failed to fetch ticket prices. Please try again later.');
        }
        setLoading(false);
    };

    const bookmark = async (ticket) => {
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
                setBookmarked(data);
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
            <h2>Concert Ticket Search</h2>
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
                                <strong>{ticket.venue} - ${ticket.price}</strong>
                                <br/>
                                <button onClick={() => bookmark(ticket)}>Bookmark</button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {showLoginScreen && (
                <div className='login-screen'>
                    <div className="banner">
                        <div className='logo'>
                        <h1>Thrift-A-Ticket</h1>
                        </div>
                    </div>
                    <div className='login-info'>
                    <h2>Login </h2>
                    <form onSubmit={handleLogin}>
                        <label htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                        <br/>
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                        <br/>
                        <button type="submit">Login</button>
                    </form>
                    {showLoginError && <p>Email or Password is incorrect</p>}
                    <button onClick={showRegisterPage}>New? Click to register</button>
                    </div>
                </div>
            )}
            {showRegisterScreen && (
                <div className='login-screen'>
                    <div className="banner">
                        <div className='logo'>
                        <h1>Thrift-A-Ticket</h1>
                        </div>
                    </div>
                    <div className='login-info'>
                    <h2>Register</h2>
                    <form onSubmit={handleRegistration}>
                        <label htmlFor="email">Email:</label>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                        <br/>
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                        <br/>
                        <button type="submit">Register</button>
                    </form>
                    {showEmailInUseError && <p>Email in use</p>}
                    <button onClick={showLoginPage}>Login</button>
                    </div>
                </div>
            )}
            {showBookmarkedTickets && (
                <div className="login-screen">
                    <button onClick={() => setBookmarkedTickets(false)}>Back</button>
                    <h2>Bookmarked Tickets</h2>
                    <ul>
                        {bookmarked.map((ticket, index) => (
                            <li key={index}>
                                <strong>{ticket.venue} - ${ticket.price}</strong>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

export default App;
