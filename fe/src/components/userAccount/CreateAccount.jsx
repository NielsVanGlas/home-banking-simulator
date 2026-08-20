import React, { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { publicAxios } from '../../axios';
import { Box, Button, TextField, Typography, Alert, Container, MenuItem, Select, InputLabel, FormControl, FormControlLabel, Checkbox, Paper, Grid, Link } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import PrivacyPolicy from '../../pages/PrivacyPolicy';
import TermsOfService from '../../pages/TermsOfService';

const CreateAccount = () => {

    const [formData, setFormData] = useState({
        password: null,
        firstName: null,
        lastName: null,
        gender: 'NA',
        bornDate: null,
        birthCity: null,
        birthProvinceCode: null,
        birthZipCode: null,
        taxCode: null,
        email: null,
        mobile: null,
        residence: {
            address: null,
            city: null,
            zipCode: null,
            provinceCode: null,
            countryCode: null
        },
        home: {
            address: null,
            city: null,
            zipCode: null,
            provinceCode: null,
            countryCode: null
        },
        marketingConsensus: false,
        serviceTermsAndConditions: false,
        documentType: 'ID',
        documentId: null,
    });

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [sameAsResidence, setSameAsResidence] = useState(true);
    const [privacyOpen, setPrivacyOpen] = useState(false);
    const [termsOpen, setTermsOpen] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleAddressChange = (type, field, value) => {
        setFormData({
            ...formData,
            [type]: { ...formData[type], [field]: value },
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        try {
            await publicAxios.post('/register', formData);
            setSuccess('Account creato! Controlla la tua email per verificare l\'account.');
            setTimeout(() => navigate('/login'), 3000);
        } catch (err) {
            setError('Errore durante la registrazione.');
            console.error(err);
        }
    };

    return (
        <Container maxWidth='false' sx={{ width: '100%', height: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', py: 2 }}>
            <Paper elevation={10} sx={{ p: { xs: 2.5, sm: 3 }, width: '100%', maxWidth: 1800, maxHeight: '95vh', overflow: 'auto', borderRadius: 3, }}>

                <Box sx={{ textAlign: 'center', mb: 3 }}>
                    <Typography variant="h4" fontWeight="bold">Registrazione</Typography>
                </Box>

                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                {success && <Alert severity="success" sx={{ mb: 2 }}>{success}</Alert>}

                <Box component="form" onSubmit={handleSubmit}>

                    <Typography variant="h6" gutterBottom>Anagrafica</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Nome *" name="firstName" value={formData.firstName} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Cognome *" name="lastName" value={formData.lastName} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Codice Fiscale" name="taxCode" value={formData.taxCode} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField select fullWidth size="small" label="Sesso" name="gender" value={formData.gender} onChange={handleChange}>
                                <MenuItem value="M">Maschio</MenuItem>
                                <MenuItem value="F">Femmina</MenuItem>
                                <MenuItem value="NA">Non specificato</MenuItem>
                            </TextField>
                        </Grid>
                        <Grid item xs={12}>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DesktopDatePicker
                                    label="Data di nascita"
                                    value={formData.bornDate ? dayjs(formData.bornDate) : null}
                                    onChange={(v) => setFormData({ ...formData, bornDate: v ? v.format('YYYY-MM-DD') : '' })}
                                    slotProps={{ textField: { fullWidth: true, size: 'small' } }}
                                    maxDate={dayjs().subtract(18, 'year')}
                                />
                            </LocalizationProvider>
                        </Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth size="small" label="Nato a" name="birthCity" value={formData.birthCity} onChange={handleChange} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Provincia" name="birthProvinceCode" value={formData.birthProvinceCode} onChange={handleChange} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="CAP" name="birthZipCode" value={formData.birthZipCode} onChange={handleChange} /></Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Contatti</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={8}><TextField fullWidth size="small" label="E-mail *" name="email" value={formData.email} onChange={handleChange} /></Grid>
                        <Grid item xs={12} sm={4}><TextField fullWidth size="small" label="Cellulare" name="mobile" value={formData.mobile} onChange={handleChange} /></Grid>
                    </Grid>

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Residenza</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}><TextField fullWidth size="small" label="Indirizzo" value={formData.residence.address} onChange={(e) => handleAddressChange('residence', 'address', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth size="small" label="Città" value={formData.residence.city} onChange={(e) => handleAddressChange('residence', 'city', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="CAP" value={formData.residence.zipCode} onChange={(e) => handleAddressChange('residence', 'zipCode', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Provincia" value={formData.residence.provinceCode} onChange={(e) => handleAddressChange('residence', 'provinceCode', e.target.value)} /></Grid>
                        <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Stato" value={formData.residence.countryCode} onChange={(e) => handleAddressChange('residence', 'countryCode', e.target.value)} /></Grid>
                    </Grid>
                    <FormControlLabel
                        control={<Checkbox checked={!sameAsResidence} onChange={(e) => setSameAsResidence(!e.target.checked)} color="primary" />}
                        label="Domicilio diverso dalla residenza"
                        sx={{ mt: 2, mb: 1 }}
                    />
                    {!sameAsResidence && (
                        <>
                            <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>
                                Domicilio
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}><TextField fullWidth size="small" label="Indirizzo" value={formData.home.address} onChange={(e) => handleAddressChange('home', 'address', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={6}><TextField fullWidth size="small" label="Città" value={formData.home.city} onChange={(e) => handleAddressChange('home', 'city', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="CAP" value={formData.home.zipCode} onChange={(e) => handleAddressChange('home', 'zipCode', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Provincia" value={formData.home.provinceCode} onChange={(e) => handleAddressChange('home', 'provinceCode', e.target.value)} /></Grid>
                                <Grid item xs={12} sm={3}><TextField fullWidth size="small" label="Stato" value={formData.home.countryCode} onChange={(e) => handleAddressChange('home', 'countryCode', e.target.value)} /></Grid>
                            </Grid>

                        </>
                    )}

                    <Typography variant="h6" gutterBottom sx={{ mt: 3 }}>Documento</Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <FormControl fullWidth>
                                <InputLabel>Tipo documento</InputLabel>
                                <Select name="documentType" value={formData.documentType} onChange={handleChange} label="Tipo documento" size="small" >
                                    <MenuItem value="ID">Carta d'identità</MenuItem>
                                    <MenuItem value="PASSPORT">Passaporto</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth size="small" label="Numero documento" name="documentId" value={formData.documentId} onChange={handleChange} />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField fullWidth size="small" type="password" label="Password *" name="password" value={formData.password} onChange={handleChange} />
                        </Grid>
                    </Grid>

                    <Box sx={{ mt: 4 }}>
                        <FormControlLabel required control={<Checkbox checked={formData.serviceTermsAndConditions} onChange={(e) => setFormData((prev) => ({ ...prev, serviceTermsAndConditions: e.target.checked }))} color="primary" />}
                            label={
                                <Typography variant="body2">
                                    Accetto i{' '}
                                    <Link
                                        component="button"
                                        variant="body2"
                                        onClick={(e) => {
                                            e.preventDefault();
                                            setTermsOpen(true);
                                        }}
                                        sx={{ textDecoration: 'underline', color: 'primary.main' }}
                                    >
                                        Termini di Servizio
                                    </Link>{' '}
                                    e l'
                                    <Link
                                        component="button"
                                        variant="body2"
                                        onClick={(e) => {
                                            e.preventDefault();
                                            setPrivacyOpen(true);
                                        }}
                                        sx={{ textDecoration: 'underline', color: 'primary.main' }}
                                    >
                                        Informativa Privacy
                                    </Link>
                                </Typography>
                            }
                        />

                        <FormControlLabel
                            control={<Checkbox checked={formData.marketingConsensus} onChange={(e) => setFormData((prev) => ({ ...prev, marketingConsensus: e.target.checked }))} color="primary" />}
                            label="Voglio ricevere offerte promozionali"
                        />
                    </Box>

                    <Button type="submit" fullWidth variant="contained" size="large" disabled={!formData.serviceTermsAndConditions} sx={{ mt: 3, mb: 2, py: 1.5 }} >
                        Registrati
                    </Button>
                    Hai già un account?  <RouterLink to="/login" variant="body2">
                        Accedi
                    </RouterLink>
                </Box>
            </Paper>
            <PrivacyPolicy open={privacyOpen} onClose={() => setPrivacyOpen(false)} />
            <TermsOfService open={termsOpen} onClose={() => setTermsOpen(false)} />
        </Container>
    );
};

export default CreateAccount;