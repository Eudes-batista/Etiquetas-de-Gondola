package etiquetas.controle;

import etiquetas.modelo.Configuracao;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoControle {

    private final Path path;
    private Configuracao configuracao;

    public ArquivoControle() throws IOException {
        this.path = Paths.get("configuracao.txt");
        if (verificarArquivo()) {
            criarArquivo();
        }
    }

    public void criarArquivo() throws IOException {
        File file = path.toFile();
        file.setExecutable(true, true);
        file.setReadable(true, true);
        file.setWritable(true, true);
        file.createNewFile();
    }

    public boolean verificarArquivo() {
        return Files.notExists(this.path);
    }

    public void escreverNoArquivo(Configuracao configuracao) throws IOException {
        this.configuracao = null;
        this.configuracao = configuracao;
        try (PrintWriter printWriter = new PrintWriter(this.path.toFile())) {
            printWriter.println(this.configuracao.getHost());
            printWriter.println(this.configuracao.getCaminho());
            printWriter.println(this.configuracao.getEmpresa());
            printWriter.println(this.configuracao.getTipoEtiqueta());
            printWriter.println(this.configuracao.getImpressora());
            printWriter.println(this.configuracao.getCompartilhamentoImpressora());
            printWriter.flush();
        }
    }

    public Configuracao lerArquivo() throws IOException {
        this.configuracao = null;
        this.configuracao = new Configuracao();
        try (BufferedReader newBufferedReader = Files.newBufferedReader(this.path)) {
            this.configuracao.setHost(newBufferedReader.readLine());
            this.configuracao.setCaminho(newBufferedReader.readLine());
            this.configuracao.setEmpresa(newBufferedReader.readLine());
            this.configuracao.setTipoEtiqueta(newBufferedReader.readLine());
            this.configuracao.setImpressora(newBufferedReader.readLine());
            this.configuracao.setCompartilhamentoImpressora(newBufferedReader.readLine());
        }
        return this.configuracao;
    }
}
