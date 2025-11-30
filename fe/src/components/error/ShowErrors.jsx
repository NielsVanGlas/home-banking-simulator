import React, { useState, useEffect, forwardRef, useImperativeHandle } from 'react';
import axiosInstance from '../../axios';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, CircularProgress, Alert, Chip, Box, Typography } from '@mui/material';
import { Error as ErrorIcon } from '@mui/icons-material';

const ShowErrors = forwardRef((props, ref) => {
    const [errors, setErrors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(15);
    const [totalItems, setTotalItems] = useState(0);

    const fetchErrors = async () => {
        setLoading(true);
        setError('');
        try {
            const res = await axiosInstance.get('/errors', {
                params: {
                    page: page + 1,
                    size: rowsPerPage,
                    sort: 'createdAt,desc'
                }
            });

            const data = res.data;
            setErrors(data.item || []);
            setTotalItems(data.totalItems || 0);
        } catch (err) {
            setError('Impossibile caricare il log errori');
            console.error(err);
            setErrors([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchErrors();
    }, [page, rowsPerPage]);

    useImperativeHandle(ref, () => ({
        refetch: fetchErrors
    }));

    const handlePageChange = (e, newPage) => setPage(newPage);
    const handleRowsPerPageChange = (e) => {
        setRowsPerPage(parseInt(e.target.value, 10));
        setPage(0);
    };

    const formatDate = (dateString) => {
        if (!dateString) return 'N/D';
        return new Date(dateString).toLocaleString('it-IT', {
            day: '2-digit',
            month: 'short',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    };

    if (loading) {
        return (
            <Paper elevation={3}>
                <Box sx={{ py: 8, textAlign: 'center' }}>
                    <CircularProgress />
                </Box>
            </Paper>
        );
    }

    if (error) {
        return (
            <Paper elevation={3}>
                <Alert severity="error" sx={{ m: 4 }}>{error}</Alert>
            </Paper>
        );
    }

    return (
        <Paper elevation={3}>
            <Box sx={{ p: 3, backgroundColor: 'error.main', color: 'white' }}>
                <Typography variant="h6" sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <ErrorIcon /> Log Errori Sistema
                </Typography>
                <Typography variant="body2">
                    Errori catturati dal sistema – Ordinati per data (più recente prima)
                </Typography>
            </Box>

            <TableContainer>
                <Table stickyHeader size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell><strong>Data e Ora</strong></TableCell>
                            <TableCell><strong>Stato HTTP</strong></TableCell>
                            <TableCell><strong>Messaggio Errore</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {errors.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={3} align="center" sx={{ py: 6 }}>
                                    <Typography color="success.main" variant="h6">
                                        Nessun errore registrato
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            errors.map((err, index) => (
                                <TableRow
                                    key={index}
                                    hover
                                    sx={{
                                        backgroundColor:
                                            err.status?.value >= 500
                                                ? 'error.veryLight'
                                                : err.status?.value >= 400
                                                    ? 'warning.veryLight'
                                                    : 'grey.50'
                                    }}
                                >
                                    <TableCell>
                                        <Typography variant="body2" sx={{ fontFamily: 'monospace', fontSize: '0.85rem' }}>
                                            {formatDate(err.createdAt)}
                                        </Typography>
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={err.status?.value || 'N/D'}
                                            color={err.status?.value >= 500 ? 'error' : 'warning'}
                                            size="small"
                                            variant="outlined"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Typography variant="body2" sx={{ fontFamily: 'monospace', fontSize: '0.85rem', whiteSpace: 'pre-wrap' }}>
                                            {err.errorMessage || 'Nessun messaggio'}
                                        </Typography>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            <TablePagination
                component="div"
                count={totalItems}
                page={page}
                onPageChange={handlePageChange}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleRowsPerPageChange}
                rowsPerPageOptions={[10, 15, 25, 50, 100]}
                labelRowsPerPage="Errori per pagina:"
                labelDisplayedRows={({ from, to, count }) => `${from}–${to} di ${count}`}
            />
        </Paper>
    );
});

ShowErrors.displayName = 'ShowErrors';
export default ShowErrors;