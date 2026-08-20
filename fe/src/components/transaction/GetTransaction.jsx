import { useState, useEffect } from 'react';
import axiosInstance from '../../axios';

const GetTransaction = () => {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);

    const loadTransactions = async () => {
        try {
            setLoading(true);
            const res = await axiosInstance.get('/transaction?limit=10');
            setTransactions(res.data?.item || []);
        } catch (err) {
            console.error('Errore caricamento movimenti:', err);
            setTransactions([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadTransactions();
    }, []);

    return {
        transactions,
        loading,
        refresh: loadTransactions
    };
};

export default GetTransaction;