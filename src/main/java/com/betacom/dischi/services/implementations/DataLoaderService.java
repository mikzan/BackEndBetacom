package com.betacom.dischi.services.implementations;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.betacom.dischi.models.Comune;
import com.betacom.dischi.models.Provincia;
import com.betacom.dischi.repository.IComuneRepository;
import com.betacom.dischi.repository.IProvinciaRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataLoaderService {

    @Autowired
    private IProvinciaRepository provinciaRepository;

    @Autowired
    private IComuneRepository comuneRepository;

    @PostConstruct  // Eseguito automaticamente all'avvio
    @Transactional
    public void loadData() {
        String comuniFilePath = "data/data.csv"; // Percorso del file dei comuni
        String capFilePath = "data/cap.csv";     // Percorso del file dei CAP
        char delimiter = ','; // Separatore corretto per entrambi i file

        try (BufferedReader comuniReader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(comuniFilePath).getInputStream(), StandardCharsets.UTF_8));
             BufferedReader capReader = new BufferedReader(new InputStreamReader(
                     new ClassPathResource(capFilePath).getInputStream(), StandardCharsets.UTF_8))) {

            Map<String, String> capMap = new HashMap<>();
            
            // Parsing del file dei CAP
            String capLine;
            while ((capLine = capReader.readLine()) != null) {
                String[] capFields = capLine.split(String.valueOf(delimiter));
                if (capFields.length == 2) {
                    String codiceComune = capFields[0];
                    String cap = capFields[1];
                    capMap.put(codiceComune, cap);  // Associa il codice comune al CAP
                }
            }

            Map<String, Provincia> provinceMap = new HashMap<>();

            // Parsing del file dei comuni
            String comuniLine;
            while ((comuniLine = comuniReader.readLine()) != null) {
                String[] comuniFields = comuniLine.split(String.valueOf(delimiter));
                if (comuniFields.length == 6) {
                    String nomeComune = comuniFields[0];
                    String codiceComune = comuniFields[1];
                    String nomeProvincia = comuniFields[2];
                    String siglaProvincia = comuniFields[3];
                    String nomeRegione = comuniFields[4];
                    String codiceRegione = comuniFields[5];

                    // PROVINCIA
                    Provincia provincia = provinceMap.computeIfAbsent(siglaProvincia, k -> {
                        Provincia p = new Provincia();
                        p.setCodice(k);
                        p.setNome(nomeProvincia);
                        return provinciaRepository.save(p);
                    });

                    // COMUNE
                    Comune comune = new Comune();
                    comune.setNome(nomeComune);
                    comune.setProvincia(provincia);
                    comune.setCap(capMap.get(codiceComune));  // Impostazione del CAP

                    comuneRepository.save(comune);

                    System.out.println("Salvato Comune: " + nomeComune + " - Provincia: " + nomeProvincia);
                }
            }

        } catch (Exception e) {
            System.err.println("Errore nella lettura dei file: " + e.getMessage());
        }
    }
}
