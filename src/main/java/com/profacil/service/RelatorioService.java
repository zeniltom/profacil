package com.profacil.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Prova;
import com.profacil.model.Questao;
import com.profacil.security.ProfessorSistema;
import com.profacil.util.jsf.FacesUtil;
import com.profacil.util.report.ExecutorRelatorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	public void gerarRelatorioProva(FacesContext facesContext, HttpServletResponse response, EntityManager manager,
			ProfessorSistema professorSistema, Prova prova, List<Questao> questoesSelecionadas)
			throws IOException, JRException {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// CARREGA A LOGO DA PASTA IMAGENS E MANDA COMO PARÂMETRO
		BufferedImage image = ImageIO.read(getClass().getResource("/provas/fasete.png"));
		DateFormat format = new SimpleDateFormat("dd/MM/yyy");

		// CARREGA O SUBRELATÓRIO DE RESPOSTAS E MANDA COMO PARÂMETRO

		String caminho = getClass().getClassLoader().getResource("/provas/resposta_prova.jasper").getFile();
		String caminhoComEspaco = caminho.replace("%20", " ");

		File respostas = new File(caminhoComEspaco);
		JasperReport subRelatorioDeRespostas = (JasperReport) JRLoader.loadObject(respostas);

		// VERIFICA AS QUESTÕES SELECIONADAS E MANDA COMO PARÂMETRO
		List<Long> questoes = new ArrayList<>();
		if (questoesSelecionadas.size() > 0) {
			for (Questao q : questoesSelecionadas)
				questoes.add(q.getId());
		} else
			questoes.add((long) 0);

		// PARÂMETROS PARA O RELATÓRIO
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("curso_id", prova.getDisciplina().getCurso().getId());
		parametros.put("disciplina_id", prova.getDisciplina().getId());
		parametros.put("data_prova", prova.getDataProva());
		parametros.put("n_questoes", prova.getnQuestoes());
		parametros.put("turma", prova.getTurma());
		parametros.put("turno", prova.getTurno().getDescricao());
		parametros.put("valor", prova.getValor());
		parametros.put("professor_id", professorSistema.getProfessor().getId());
		parametros.put("logo", image);
		parametros.put("lista_questoes", questoes);
		parametros.put("subReport", subRelatorioDeRespostas);

		// NOME DO ARQUIVO PDF
		String arquivo = "prova_de_" + prova.getDisciplina().getDescricao().toLowerCase() + "_"
				+ format.format(new Date()) + ".pdf";

		ExecutorRelatorio executor = new ExecutorRelatorio("/provas/prova_gerada.jasper", response, parametros,
				arquivo);

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado())
			facesContext.responseComplete();
		else
			FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
	}
}
