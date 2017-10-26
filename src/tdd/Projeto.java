package tdd;

import java.util.Collection;
import java.util.LinkedList;

class OcorrenciaJaCadastrada extends Exception {}

public class Projeto {
	Collection<Ocorrencia> colecaoDeOcorrencias;
	
	public Projeto() {
		this.colecaoDeOcorrencias = new LinkedList<>();
	}
	
	public Collection<Ocorrencia> getOcorrencias() {
		return this.colecaoDeOcorrencias;
	}

	public void cadastrarOcorrencia(Ocorrencia novaOcorrencia) throws OcorrenciaJaCadastrada {
		if(this.colecaoDeOcorrencias.contains(novaOcorrencia))
			throw new OcorrenciaJaCadastrada();
		this.colecaoDeOcorrencias.add(novaOcorrencia);
	}
}
