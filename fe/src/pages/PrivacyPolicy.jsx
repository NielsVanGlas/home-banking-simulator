import React from 'react';
import { Dialog, DialogTitle, DialogContent, IconButton, Typography, Box, Container } from '@mui/material';
import { Close } from '@mui/icons-material';

const PrivacyPolicy = ({ open, onClose }) => {
  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth scroll="paper">
      <DialogTitle sx={{ pr: 8 }}>
        <Typography variant="h5" component="div" fontWeight="bold">
          Informativa sulla Privacy
        </Typography>
        <IconButton
          onClick={onClose}
          sx={{ position: 'absolute', right: 8, top: 8 }}
        >
          <Close />
        </IconButton>
      </DialogTitle>

      <DialogContent dividers>
        <Container maxWidth="md">
          <Box sx={{ lineHeight: 1.8, '& p': { mb: 2 } }}>
            <Typography variant="subtitle1" color="text.secondary" paragraph>
              Ai sensi del Regolamento UE 2016/679 (GDPR) – Ultimo aggiornamento: 04 dicembre 2025
            </Typography>

            <Typography paragraph>
              La tua privacy è importante per noi. Questa informativa spiega come trattiamo i tuoi dati personali.
            </Typography>

            <Typography variant="h6" gutterBottom mt={4}>1. Titolare del trattamento</Typography>
            <Typography paragraph>Home Banking S.r.l. – Via Falsa 333, 01317 Roma</Typography>

            <Typography variant="h6" gutterBottom mt={4}>2. Dati raccolti</Typography>
            <Typography paragraph>
              Raccogliamo:
              <ul>
                <li>Dati anagrafici (nome, cognome, data di nascita, codice fiscale)</li>
                <li>Dati di contatto (email, telefono)</li>
                <li>Dati identificativi (documento d'identità)</li>
                <li>Dati bancari e transazioni</li>
                <li>Dati di navigazione e log di accesso</li>
              </ul>
            </Typography>

            <Typography variant="h6" gutterBottom mt={4}>3. Finalità del trattamento</Typography>
            <Typography paragraph>
              I tuoi dati sono utilizzati per:
              <ul>
                <li>Erogare il servizio di home banking</li>
                <li>Adempiere agli obblighi di legge (antiriciclaggio, PSD2)</li>
                <li>Prevenire frodi e garantire la sicurezza</li>
                <li>Inviarti comunicazioni di servizio</li>
                <li>(Solo con consenso) Inviare offerte promozionali</li>
              </ul>
            </Typography>

            <Typography variant="h6" gutterBottom mt={4}>4. Base giuridica</Typography>
            <Typography paragraph>
              Il trattamento è basato su contratto, obblighi di legge, interesse legittimo e sul tuo consenso.
            </Typography>

            <Typography variant="h6" gutterBottom mt={4}>5. Diritti dell'interessato</Typography>
            <Typography paragraph>
              Puoi in ogni momento esercitare i tuoi diritti chiedendoci la cancellazione dei dati.
            </Typography>

            <Typography paragraph mt={6} color="text.secondary">
              Grazie per la fiducia.
            </Typography>
          </Box>
        </Container>
      </DialogContent>
    </Dialog>
  );
};

export default PrivacyPolicy;