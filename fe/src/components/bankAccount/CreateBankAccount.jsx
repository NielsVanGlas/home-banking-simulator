import { useState, useEffect } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, IconButton, Box, TextField, Select, MenuItem as MuiMenuItem, FormControl, InputLabel, FormHelperText, Button, Alert, CircularProgress } from '@mui/material';
import { Close } from '@mui/icons-material';
import GetCurrency from '../currency/GetCurrency'

const CreateBankAccount = ({ open, onClose, onSuccess, axiosInstance }) => {

    const [formData, setFormData] = useState({
        name: '',
        currency: '',
        balance: '',
        balanceDate: new Date().toISOString().split('T')[0]
    });

    const { currencies, loading: loadingCurrencies } = GetCurrency({
        axiosInstance,
        enabled: open,
        onLoaded: (list) => {
            if (list.length > 0 && !formData.currency) {
                setFormData(prev => ({ ...prev, currency: list[0].id }));
            }
        }
    });

    const [creating, setCreating] = useState(false);

    const [createError, setCreateError] = useState('');

    const [formErrors, setFormErrors] = useState({});

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
        setFormErrors(prev => ({ ...prev, [name]: '' }));
    };

    const validateForm = () => {
        const errors = {};
        if (!formData.name.trim()) errors.name = 'Il nome è obbligatorio';
        if (!formData.currency) errors.currency = 'Seleziona una valuta';
        if (!formData.balance || Number(formData.balance) < 0) errors.balance = 'Il saldo non può essere negativo';
        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleCreateAccount = async () => {

        if (!validateForm()) {
            return;
        }

        setCreating(true);
        setCreateError('');

        try {
            const payload = {
                name: formData.name.trim(),
                currency: formData.currency,
                balance: parseFloat(formData.balance),
                balanceDate: formData.balanceDate
            };

            await axiosInstance.post('/bank', payload);

            onSuccess?.();
            handleClose();
        } catch (err) {
            const msg = err.response?.data?.message || 'Errore durante la creazione del conto';
            setCreateError(msg);
        } finally {
            setCreating(false);
        }
    };

    const handleClose = () => {
        if (creating) {
            return;
        }

        setFormData({
            name: '',
            currency: currencies[0]?.id || '',
            balance: '',
            balanceDate: new Date().toISOString().split('T')[0]
        });
        setFormErrors({});
        setCreateError('');
        onClose();
    };

    useEffect(() => {
        if (open && currencies.length > 0 && !formData.currency) {
            setFormData(prev => ({ ...prev, currency: currencies[0].id }));
        }
    }, [open, currencies, formData.currency]);

    return (
        <Dialog open={open} onClose={() => !creating && handleClose()} maxWidth="sm" fullWidth>
            <DialogTitle>
                Crea Nuovo Conto Corrente
                <IconButton
                    onClick={handleClose}
                    sx={{ position: 'absolute', right: 8, top: 8 }}
                    disabled={creating}
                >
                    <Close />
                </IconButton>
            </DialogTitle>
            <DialogContent dividers>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3, pt: 1 }}>
                    <TextField
                        label="Nome Conto"
                        name="name"
                        value={formData.name}
                        onChange={handleInputChange}
                        error={!!formErrors.name}
                        helperText={formErrors.name}
                        fullWidth
                        disabled={creating}
                    />
                    <FormControl fullWidth error={!!formErrors.currency} disabled={creating || loadingCurrencies}>
                        <InputLabel>Valuta</InputLabel>
                        <Select
                            name="currency"
                            value={formData.currency}
                            label="Valuta"
                            onChange={handleInputChange}
                        >
                            {currencies.map((curr) => (
                                <MuiMenuItem key={curr.id} value={curr.id}>
                                    {curr.name} ({curr.iso}) {curr.symbol}
                                </MuiMenuItem>
                            ))}
                        </Select>
                        {formErrors.currency && <FormHelperText>{formErrors.currency}</FormHelperText>}
                        {loadingCurrencies && <FormHelperText>Caricamento valute...</FormHelperText>}
                    </FormControl>
                    <TextField
                        label="Saldo Iniziale"
                        name="balance"
                        type="number"
                        inputProps={{ min: 0, step: "0.01" }}
                        value={formData.balance}
                        onChange={handleInputChange}
                        error={!!formErrors.balance}
                        helperText={formErrors.balance || 'Importo in valuta selezionata'}
                        fullWidth
                        disabled={creating}
                    />
                </Box>
                {createError && <Alert severity="error" sx={{ mt: 3 }}>{createError}</Alert>}
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} disabled={creating}>
                    Annulla
                </Button>
                <Button
                    onClick={handleCreateAccount}
                    variant="contained"
                    disabled={creating || loadingCurrencies}
                    startIcon={creating ? <CircularProgress size={20} /> : null}
                >
                    {creating ? 'Creazione...' : 'Crea Conto'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default CreateBankAccount;