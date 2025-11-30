import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, IconButton, Box, TextField, Button, Alert, CircularProgress } from '@mui/material';
import { Close } from '@mui/icons-material';
import axiosInstance from '../../axios';

const CreateCurrency = ({ open, onClose, onSuccess }) => {
    const [formData, setFormData] = useState({
        name: '',
        iso: '',
        symbol: '',
        exchange: ''
    });

    const [creating, setCreating] = useState(false);
    const [error, setError] = useState('');
    const [formErrors, setFormErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        setFormErrors(prev => ({ ...prev, [name]: '' }));
    };

    const validate = () => {
        const errors = {};
        if (!formData.name.trim()) errors.name = 'Nome obbligatorio';
        if (!formData.iso.trim() || formData.iso.length !== 3) errors.iso = 'ISO deve essere 3 lettere (es. EUR)';
        if (!formData.symbol.trim()) errors.symbol = 'Simbolo obbligatorio';
        if (!formData.exchange || formData.exchange < 0) errors.exchange = 'Tasso di cambio non valido';
        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = async () => {
        if (!validate()) return;

        setCreating(true);
        setError('');
        try {
            await axiosInstance.post('/currency', {
                name: formData.name.trim(),
                iso: formData.iso.trim().toUpperCase(),
                symbol: formData.symbol.trim(),
                exchange: Number(formData.exchange)
            });
            onSuccess?.();
            handleClose();
        } catch (err) {
            const msg = err.response?.data?.message || 'Errore durante la creazione';
            setError(msg);
        } finally {
            setCreating(false);
        }
    };

    const handleClose = () => {
        if (creating) return;
        setFormData({ name: '', iso: '', symbol: '', exchange: '' });
        setFormErrors({});
        setError('');
        onClose();
    };

    return (
        <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
            <DialogTitle>
                Crea Nuova Valuta
                <IconButton onClick={handleClose} disabled={creating}
                    sx={{ position: 'absolute', right: 8, top: 8 }}>
                    <Close />
                </IconButton>
            </DialogTitle>

            <DialogContent dividers>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3, pt: 1 }}>
                    <TextField
                        label="Nome Completo"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        error={!!formErrors.name}
                        helperText={formErrors.name || 'es. Euro'}
                        fullWidth
                        disabled={creating}
                    />
                    <TextField
                        label="Codice ISO (3 lettere)"
                        name="iso"
                        value={formData.iso}
                        onChange={e => handleChange({ target: { name: 'iso', value: e.target.value.toUpperCase() } })}
                        error={!!formErrors.iso}
                        helperText={formErrors.iso || 'es. EUR, USD, GBP'}
                        fullWidth
                        disabled={creating}
                        inputProps={{ maxLength: 3 }}
                    />
                    <TextField
                        label="Simbolo"
                        name="symbol"
                        value={formData.symbol}
                        onChange={handleChange}
                        error={!!formErrors.symbol}
                        helperText={formErrors.symbol || 'es. €, $, £'}
                        fullWidth
                        disabled={creating}
                    />
                    <TextField
                        label="Tasso di Cambio (vs EUR)"
                        name="exchange"
                        type="number"
                        inputProps={{ step: "0.000001" }}
                        value={formData.exchange}
                        onChange={handleChange}
                        error={!!formErrors.exchange}
                        helperText={formErrors.exchange || 'es. 1.00 per EUR, 0.85 per USD'}
                        fullWidth
                        disabled={creating}
                    />
                </Box>

                {error && <Alert severity="error" sx={{ mt: 3 }}>{error}</Alert>}
            </DialogContent>

            <DialogActions>
                <Button onClick={handleClose} disabled={creating}>Annulla</Button>
                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={creating}
                    startIcon={creating ? <CircularProgress size={20} /> : null}
                >
                    {creating ? 'Creazione...' : 'Crea Valuta'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateCurrency;