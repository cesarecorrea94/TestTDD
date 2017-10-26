package tdd;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import tdd.Ocorrencia.Estado;

class FuncionarioJaPossuiTalOcorrencia extends Exception {}
class ProjetoJaAtribuido extends Exception {}
class ExcedeOLimiteDeOcorrenciasPermitida extends Exception {}

public class Funcionario {
	
	private List<Projeto> trabalhandoEmVariosProjetosSimultaneamente;
	private List<Ocorrencia> ocorrenciasAbertas_max10;
	
	public Funcionario() {
		this.trabalhandoEmVariosProjetosSimultaneamente = new LinkedList<>();
		this.ocorrenciasAbertas_max10 = new LinkedList<>();
	}
	
	public List<Projeto> getProjetos() {
		return this.trabalhandoEmVariosProjetosSimultaneamente;
	}

	public Collection<Ocorrencia> getOcorrenciasAbertas() {
		return this.ocorrenciasAbertas_max10;
	}

	public void terminaOcorrencia(Ocorrencia ocorrencia)
			throws OcorrenciaManipuladaPorUmNaoResponsavel, OcorrenciaJaCompletada {
		if(this != ocorrencia.getFuncionarioResponsavel())
			throw new OcorrenciaManipuladaPorUmNaoResponsavel();
		if(!this.ocorrenciasAbertas_max10.contains(ocorrencia))
			throw new OcorrenciaJaCompletada();
		this.ocorrenciasAbertas_max10.remove(ocorrencia);
		if(ocorrencia.getEstado() != Estado.COMPLETADA)
			ocorrencia.completar(this);
	}

	public void atribuirProjeto(Projeto novoProjeto) throws ProjetoJaAtribuido {
		if(this.trabalhandoEmVariosProjetosSimultaneamente.contains(novoProjeto))
			throw new ProjetoJaAtribuido();
		this.trabalhandoEmVariosProjetosSimultaneamente.add(novoProjeto);
	}

	public void atribuirOcorrencia(Ocorrencia novaOcorrencia)
			throws OcorrenciaJaCompletada, FuncionarioJaPossuiTalOcorrencia, ExcedeOLimiteDeOcorrenciasPermitida {
		if(novaOcorrencia.getEstado() == Estado.COMPLETADA)
			throw new OcorrenciaJaCompletada();
		if(this.ocorrenciasAbertas_max10.contains(novaOcorrencia))
			throw new FuncionarioJaPossuiTalOcorrencia();
		if(this.ocorrenciasAbertas_max10.size() < 10) {
			this.ocorrenciasAbertas_max10.add(novaOcorrencia);
			novaOcorrencia.setResponsavel(this);
		}
		else throw new ExcedeOLimiteDeOcorrenciasPermitida();
	}

	void removeOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrenciasAbertas_max10.remove(ocorrencia);
	}
}
