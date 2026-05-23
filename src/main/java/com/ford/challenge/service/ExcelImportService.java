package com.ford.challenge.service;

import com.ford.challenge.model.EspecificacaoFord;
import com.ford.challenge.repository.EspecificacaoFordRepository;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Iterator;

@Service
public class ExcelImportService {

    private final EspecificacaoFordRepository repository;

    public ExcelImportService(EspecificacaoFordRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void importarDadosDoExcel() {
        if (repository.count() > 0) return;

        try (InputStream is = getClass().getResourceAsStream("/FIAP-Ford - Data sheet_Desafio_01_v02.xlsx")) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira aba
            Iterator<Row> rowIterator = sheet.iterator();

            String categoriaAtual = "Geral";

            // Pula as primeiras linhas de cabeçalho (ajuste conforme o arquivo)
            if (rowIterator.hasNext()) rowIterator.next();
            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Pega o valor da primeira célula (Equipamento ou Categoria)
                Cell cell0 = row.getCell(0);
                if (cell0 == null || cell0.toString().isEmpty()) continue;

                String valorCell0 = cell0.toString();

                // Lógica para detectar se é uma linha de CATEGORIA (ex: "Engine & Transmission")
                // Geralmente categorias no Excel não têm valores nas colunas seguintes
                Cell cell1 = row.getCell(1);
                if (cell1 == null || cell1.toString().isEmpty()) {
                    categoriaAtual = valorCell0;
                    continue;
                }

                // Se chegou aqui, é um dado técnico
                EspecificacaoFord spec = new EspecificacaoFord();
                spec.setCategoria(categoriaAtual);
                spec.setEquipamento(valorCell0);
                spec.setVersaoXlt(getCellValue(row.getCell(1)));
                spec.setVersaoLimited(getCellValue(row.getCell(2)));
                spec.setVersaoLimitedPlus(getCellValue(row.getCell(3)));

                repository.save(spec);
            }
            workbook.close();
            System.out.println("✅ Dados do Excel Ford importados com sucesso!");

        } catch (Exception e) {
            System.err.println("❌ Erro ao ler Excel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Função auxiliar para ler qualquer tipo de célula (Número ou Texto) como String
    private String getCellValue(Cell cell) {
        if (cell == null) return "0";
        switch (cell.getCellType()) {
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case STRING: return cell.getStringCellValue();
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "0";
        }
    }
}