import { useState, useEffect } from 'react';

const GetCurrency = ({ axiosInstance, enabled = false }) => {
    const [currencies, setCurrencies] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!enabled) {
            setCurrencies([]);
            setLoading(false);
            return;
        }

        const load = async () => {
            try {
                setLoading(true);
                const res = await axiosInstance.get('/currency');
                const list = res.data?.item || res.data?.content || [];
                setCurrencies(list);
            } catch (err) {
                console.error('Errore caricamento valute:', err);
                setCurrencies([]);
            } finally {
                setLoading(false);
            }
        };

        load();
    }, [axiosInstance, enabled]);

    return { currencies, loading };
};

export default GetCurrency;