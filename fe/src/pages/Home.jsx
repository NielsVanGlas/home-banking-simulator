import React, { useContext, useState } from 'react';
import { AuthenticationContext } from '../components/auth/AuthenticationContext';
import axiosInstance from '../axios';
import { Container, Box, Typography, AppBar, Toolbar, IconButton, Avatar, Menu, MenuItem, Divider, ListItemIcon, Button, CircularProgress, Paper, Grid, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import { AccountBalance, Logout, Edit, AddCard, DeleteForever, AdminPanelSettings, Security, Description, EuroSymbol, ReceiptLong } from '@mui/icons-material';
import CreateBankAccount from '../components/bankAccount/CreateBankAccount';
import DeleteBankAccount from '../components/bankAccount/DeleteBankAccount';
import CreateTransaction from '../components/transaction/CreateTransaction';
import UpdateAccount from '../components/userAccount/UpdateAccount';
import DeleteAccount from '../components/userAccount/DeleteAccount';
import PrivacyPolicy from './PrivacyPolicy';
import TermsOfService from './TermsOfService';
import GetBankAccount from '../components/bankAccount/GetBankAccount';
import GetTransaction from '../components/transaction/GetTransaction';

const Home = () => {
    const { user, isLoading: authLoading, logout } = useContext(AuthenticationContext);

    const [anchorEl, setAnchorEl] = useState(null);
    const [createBankOpen, setCreateBankOpen] = useState(false);
    const [deleteBankOpen, setDeleteBankOpen] = useState(false);
    const [createTxOpen, setCreateTxOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);
    const [privacyOpen, setPrivacyOpen] = useState(false);
    const [termsOpen, setTermsOpen] = useState(false);

    const handleMenuOpen = (e) => setAnchorEl(e.currentTarget);
    const handleMenuClose = () => setAnchorEl(null);

    const {
        bankAccount,
        loading: loadingBank,
        refresh: refreshBank
    } = GetBankAccount();

    const {
        transactions,
        loading: loadingTx,
        refresh: refreshTx
    } = GetTransaction();

    const refreshAll = () => {
        refreshBank();
        refreshTx();
    };

    if (authLoading || !user) {
        return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 10 }}><CircularProgress /></Box>;
    }

    const initials = `${user.firstName?.[0] || ''}${user.lastName?.[0] || ''}`;

    return (
        <>
            <AppBar position="static" color="transparent" elevation={0} sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Toolbar>
                    <AccountBalance sx={{ mr: 2, fontSize: 30 }} />
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>Home Banking</Typography>
                    <Typography variant="subtitle1" sx={{ mr: 2 }}>
                        {user.firstName} {user.lastName}
                    </Typography>
                    <IconButton onClick={handleMenuOpen}>
                        <Avatar sx={{ bgcolor: 'primary.main' }}>{initials}</Avatar>
                    </IconButton>
                    <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                        <MenuItem onClick={() => { setEditOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Edit fontSize="small" /></ListItemIcon>
                            Modifica Profilo
                        </MenuItem>
                        {user.role === 'ADMIN' && (
                            <>
                                <Divider />
                                <MenuItem onClick={() => { handleMenuClose(); window.location.href = '/admin'; }}>
                                    <ListItemIcon><AdminPanelSettings fontSize="small" /></ListItemIcon>
                                    Pannello Admin
                                </MenuItem>
                            </>
                        )}
                        <Divider />
                        <MenuItem onClick={() => { setPrivacyOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Security fontSize="small" /></ListItemIcon>
                            Privacy
                        </MenuItem>
                        <MenuItem onClick={() => { setTermsOpen(true); handleMenuClose(); }}>
                            <ListItemIcon><Description fontSize="small" /></ListItemIcon>
                            Termini di Servizio
                        </MenuItem>
                        <Divider />
                        <MenuItem onClick={logout}>
                            <ListItemIcon><Logout fontSize="small" /></ListItemIcon>
                            Esci
                        </MenuItem>
                        <DeleteAccount />
                    </Menu>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 6 }}>
                <Typography variant="h4" gutterBottom>
                    Benvenuto, {user.firstName}!
                </Typography>

                <Grid container spacing={4}>
                    <Grid item xs={12} md={bankAccount ? 8 : 12}>
                        <Paper elevation={3} sx={{ p: 4, height: '100%' }}>
                            <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
                                <Typography variant="h6">Conto Corrente</Typography>
                                {bankAccount && (
                                    <Button
                                        size="small"
                                        color="error"
                                        startIcon={<DeleteForever />}
                                        onClick={() => setDeleteBankOpen(true)}
                                    >
                                        Elimina
                                    </Button>
                                )}
                            </Box>

                            {loadingBank ? (
                                <Box textAlign="center" py={6}><CircularProgress /></Box>
                            ) : bankAccount ? (
                                <>
                                    <Typography color="text.secondary">
                                        {bankAccount.name} • {bankAccount.iban}
                                    </Typography>
                                    <Box sx={{ mt: 4, textAlign: 'center' }}>
                                        <Typography variant="h3" fontWeight="bold" color="success.main">
                                            {Number(bankAccount.balance).toFixed(2)} {bankAccount.currency?.symbol || 'EUR'}
                                        </Typography>
                                        <Typography color="text.secondary" variant="body2">
                                            Aggiornato al {new Date(bankAccount.balanceDate).toLocaleDateString('it-IT')}
                                        </Typography>
                                    </Box>
                                </>
                            ) : (
                                <Box textAlign="center" py={6}>
                                    <ReceiptLong sx={{ fontSize: 80, color: 'grey.400', mb: 3 }} />
                                    <Typography variant="h6" gutterBottom>Non hai un conto corrente</Typography>
                                    <Button
                                        variant="contained"
                                        size="large"
                                        startIcon={<AddCard />}
                                        onClick={() => setCreateBankOpen(true)}
                                    >
                                        Crea il tuo primo conto
                                    </Button>
                                </Box>
                            )}
                        </Paper>
                    </Grid>

                    {bankAccount && (
                        <Grid item xs={12}>
                            <Paper elevation={3} sx={{ p: 4 }}>
                                <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
                                    <Typography variant="h6">Movimenti Recenti</Typography>
                                    <Button
                                        variant="contained"
                                        size="small"
                                        startIcon={<EuroSymbol />}
                                        onClick={() => setCreateTxOpen(true)}
                                    >
                                        Nuovo Movimento
                                    </Button>
                                </Box>

                                {loadingTx ? (
                                    <Box textAlign="center" py={6}><CircularProgress /></Box>
                                ) : transactions.length === 0 ? (
                                    <Typography color="text.secondary" textAlign="center" py={6}>
                                        Nessun movimento registrato.
                                    </Typography>
                                ) : (
                                    <TableContainer>
                                        <Table size="small">
                                            <TableHead>
                                                <TableRow>
                                                    <TableCell><strong>Data</strong></TableCell>
                                                    <TableCell><strong>Descrizione</strong></TableCell>
                                                    <TableCell align="right"><strong>Importo</strong></TableCell>
                                                </TableRow>
                                            </TableHead>
                                            <TableBody>
                                                {transactions.map((t) => (
                                                    <TableRow key={t.id} hover>
                                                        <TableCell>{new Date(t.dateTime).toLocaleDateString('it-IT')}</TableCell>
                                                        <TableCell>{t.cause || <em>Operazione bancaria</em>}</TableCell>
                                                        <TableCell align="right" sx={{
                                                            fontWeight: 'bold',
                                                            color: t.value > 0 ? 'success.main' : 'error.main'
                                                        }}>
                                                            {t.value > 0 ? '+' : ''}{Number(t.value).toFixed(2)} {bankAccount.currency?.symbol || 'EUR'}
                                                        </TableCell>
                                                    </TableRow>
                                                ))}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                )}
                            </Paper>
                        </Grid>
                    )}
                </Grid>
            </Container>

            <CreateBankAccount
                open={createBankOpen}
                onClose={() => setCreateBankOpen(false)}
                onSuccess={refreshAll}
                axiosInstance={axiosInstance}
            />

            <DeleteBankAccount
                open={deleteBankOpen}
                onClose={() => setDeleteBankOpen(false)}
                onDeleted={refreshAll}
            />

            <CreateTransaction
                open={createTxOpen}
                onClose={() => setCreateTxOpen(false)}
                onSuccess={refreshAll}
            />

            <UpdateAccount open={editOpen} onClose={() => setEditOpen(false)} user={user} />
            <PrivacyPolicy open={privacyOpen} onClose={() => setPrivacyOpen(false)} />
            <TermsOfService open={termsOpen} onClose={() => setTermsOpen(false)} />
        </>
    );
};

export default Home;