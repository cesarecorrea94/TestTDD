package tdd;

class OcorrenciaJaCompletada extends Exception {}
class OcorrenciaManipuladaPorUmNaoResponsavel extends Exception {}

public class Ocorrencia {
	enum Tipo		{TAREFA, BUG, MELHORIA};
	enum Estado		{NOVA, ABERTA, COMPLETADA};
	enum Prioridade {ALTA, MEDIA, BAIXA};
	
	private static long contador = 0;

	private final long chave = contador++;
	private String resumoDoQueSeTrata;
	private Funcionario funcionarioResponsavel;
	private final Tipo tipo;
	private Estado estado;
	private Prioridade prioridade;
	
	public Ocorrencia(String resumo, Funcionario responsavel, Tipo tipo) throws ExcedeOLimiteDeOcorrenciasPermitida {
		this.estado = Estado.NOVA;
		this.prioridade = Prioridade.MEDIA;
		this.tipo = tipo;
		this.resumoDoQueSeTrata = resumo;
		try {	responsavel.atribuirOcorrencia(this);
		} catch (OcorrenciaJaCompletada | FuncionarioJaPossuiTalOcorrencia e) {}
	}
	public long getChave() {
		return this.chave;
	}
	public String getResumo() {
		return this.resumoDoQueSeTrata;
	}
	public Funcionario getFuncionarioResponsavel() {
		return this.funcionarioResponsavel;
	}
	public Estado getEstado() {
		return this.estado;
	}
	public Tipo getTipo() {
		return this.tipo;
	}
	public Prioridade getPrioridade() {
		return this.prioridade;
	}
	public void setPrioridade(Prioridade novaPrioridade) throws OcorrenciaJaCompletada {
		if(this.estado == Estado.COMPLETADA)
			throw new OcorrenciaJaCompletada();
		this.prioridade = novaPrioridade;
	}
	void setResponsavel(Funcionario novoResponsavel) throws OcorrenciaJaCompletada, FuncionarioJaPossuiTalOcorrencia {
		if(this.estado == Estado.COMPLETADA)
			throw new OcorrenciaJaCompletada();
		if(novoResponsavel == this.funcionarioResponsavel)
			throw new FuncionarioJaPossuiTalOcorrencia();
		if(this.estado == Estado.ABERTA)
			this.funcionarioResponsavel.removeOcorrencia(this);
		else this.estado = Estado.ABERTA;
		this.funcionarioResponsavel = novoResponsavel;
	}
	void completar(Funcionario responsavel) throws OcorrenciaManipuladaPorUmNaoResponsavel, OcorrenciaJaCompletada {
		if(responsavel != this.funcionarioResponsavel)
			throw new OcorrenciaManipuladaPorUmNaoResponsavel();
		if(this.estado == Estado.COMPLETADA)
			throw new OcorrenciaJaCompletada();
		this.estado = Estado.COMPLETADA;
		if(responsavel.getOcorrenciasAbertas().contains(this))
			responsavel.terminaOcorrencia(this);
	}
}
