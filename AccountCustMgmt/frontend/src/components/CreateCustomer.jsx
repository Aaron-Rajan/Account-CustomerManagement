import axios from 'axios';
import React, { useEffect, useState } from "react";

const CreateCustomer = () => {
    const [newName, setNewName]  = useState('');
    const [newStreetNumber, setNewStreetNumber] = useState('');
    const [newPostalCode, setNewPostalCode] = useState('');
    const [newCity, setNewCity] = useState('');
    const [newProvince, setNewProvince] = useState('');
    const [newCustomerType, setNewCustomerType] = useState('');
    const api = "/api/customers";

    const createNewCustomer = async (event) => {
        event.preventDefault();
        try {
            const customerData = {
                name: newName,
                address: {
                    streetNumber: newStreetNumber,
                    postalCode: newPostalCode,
                    city: newCity,
                    province: newProvince,
                },
                customerType: newCustomerType,
            };
            const resp = await axios.post(api, customerData, {
                headers: {"Content-Type": "application/json"}
            });

            setNewName("");
            setNewStreetNumber("");
            setNewPostalCode("");
            setNewCity("");
            setNewProvince("");
            setCustomerType("");
        } catch (error) {
            console.log("Error creating new customer: ", error);
        }
    }

    return (
        <div>
            <h1>Create A Customer!</h1>
            <form onSubmit={createNewCustomer}>
                Name: <input type="text" name="name" value={newName}
                onChange={event => setNewName(event.target.value)}/>
                Street Number: <input type="text" name="streetNumber" value={newStreetNumber}
                onChange={event => setNewStreetNumber(event.target.value)}/>
                Postal Code: <input type="text" name="postalCode" value={newPostalCode}
                onChange={event => setNewPostalCode(event.target.value)}/>
                City: <input type="text" name="city" value={newCity}
                onChange={event => setNewCity(event.target.value)}/>
                Province: <input type="text" name="province" value={newProvince}
                onChange={event => setNewProvince(event.target.value)}/>
                Customer Type: <input type="text" name="customerType" value={newCustomerType}
                onChange={event => setNewCustomerType(event.target.value)}/>
                <button type='submit'>Create!</button>
            </form>
        </div>
    )
}

export default CreateCustomer;