import axios from 'axios';
import React, { useEffect, useState } from "react";
import '../styles/ShowCustomers.css'

const ShowCustomers = () => {
    const [customers, setCustomers] = useState([]);

    const api = '/api/customers';

    useEffect(() => {
        getAllCustomers();
    }, []);

    const getAllCustomers = async () => {
        try {
            const resp = await axios.get(api);
            setCustomers(resp.data);
        } catch (error) {
            console.log("Error getting customers: ", error)
        }
    }

    return (
        <div>
            <h1>Customers:</h1>
            {customers.length > 0 ? (
                customers.map((c) => (
                <div key={c.customerId} style={{ marginBottom: "1rem" }}>
                    <h2>{c.name}</h2>
                    <p>
                    {c.address
                        ? `${c.address.postalCode ?? ""} ${c.address.city ?? ""}, ${
                            c.address.province ?? ""
                        }`
                        : "No address"}
                    </p>
                    <p>Accounts:</p>
                    <ul className="accountList">
                    {Array.isArray(c.accounts) && c.accounts.map(id => (
                        <li key={id}>Account ID{id}</li>
                    ))}
                    </ul>
                    <small>ID: {c.customerId}</small>
                </div>
                ))
            ) : (
                <p>No customers yet</p>
      )}
        </div>
    )
}

export default ShowCustomers;