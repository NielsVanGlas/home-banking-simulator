import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, IconButton, Box, TextField, Button, Alert, CircularProgress } from '@mui/material';
import { Close } from '@mui/icons-material';
import axiosInstance from '../../axios';

const UpdateCurrency = ({ open, onClose, currency, onSuccess }) => {
    const [formData, setFormData] = useState({
        name: '',
        iso: '',
        symbol: '',
        exchange: ''
    });

    const [saving, setSaving] = useState(false);
    const [error, setError] = useState('');
    const [formErrors, setFormErrors] = useState({});

    useEffect(() => {
        if (currency) {
            setFormData({
                name: currency.name || '',
                iso: currency.iso || '',
                symbol: currency.symbol || '',
                exchange: currency.exchange || ''
            });
        }
    }, [currency]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        setFormErrors(prev => ({ ...prev, [name]: '' }));
    };

    const validate = () => {
        const errors = {};
        if (!formData.name.trim()) errors.name = 'Nome obbligatorio';
        if (!formData.iso.trim() || formData.iso.length !== 3) errors.iso = 'ISO deve essere 3 lettere';
        if (!formData.symbol.trim()) errors.symbol = 'Simbolo obbligatorio';
        if (!formData.exchange || formData.exchange < 0) errors.exchange = 'Tasso non valido';
        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = async () => {
        if (!validate()) return;

        setSaving(true);
        setError('');
        try {
            await axiosInstance.put(`/currency/${currency.id}`, {
                name: formData.name.trim(),
                iso: formData.iso.trim().toUpperCase(),
                symbol: formData.symbol.trim(),
                exchange: Number(formData.exchange)
            });
            onSuccess?.();
            onClose();
        } catch (err) {
            const msg = err.response?.data?.message || 'Errore durante il salvataggio';
            setError(msg);
        } finally {
            setSaving(false);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                Modifica Valuta
                <IconButton onClick={onClose} disabled={saving}
                    sx={{ position: 'absolute', right: 8, top: 8 }}>
                    <Close />
                </IconButton>
            </DialogTitle>

            <DialogContent dividers>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3, pt: 1 }}>
                    <TextField label="Nome Completo" name="name" value={formData.name} onChange={handleChange}
                        error={!!formErrors.name} helperText={formErrors.name} fullWidth disabled={saving} />
                    <TextField label="Codice ISO" name="iso" value={formData.iso}
                        onChange={e => handleChange({ target: { name: 'iso', value: e.target.value.toUpperCase() } })}
                        error={!!formErrors.iso} helperText={formErrors.iso} fullWidth disabled={saving} inputProps={{ maxLength: 3 }} />
                    <TextField label="Simbolo" name="symbol" value={formData.symbol} onChange={handleChange}
                        error={!!formErrors.symbol} helperText={formErrors.symbol} fullWidth disabled={saving} />
                    <TextField label="Tasso di Cambio (vs EUR)" name="exchange" type="number" inputProps={{ step: "0.000001" }}
                        value={formData.exchange} onChange={handleChange} error={!!formErrors.exchange}
                        helperText={formErrors.exchange} fullWidth disabled={saving} />
                </Box>

                {error && <Alert severity="error" sx={{ mt: 3 }}>{error}</Alert>}
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose} disabled={saving}>Annulla</Button>
                <Button variant="contained" onClick={handleSubmit} disabled={saving}
                    startIcon={saving ? <CircularProgress size={20} /> : null}>
                    {saving ? 'Salvataggio...' : 'Salva Modifiche'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default UpdateCurrency;