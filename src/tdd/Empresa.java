package tdd;

import java.util.LinkedList;
import java.util.List;

class FuncionarioJaCadastrado extends Exception {}
class ProjetoJaCadastrado extends Exception {}

public class Empresa {
	List<Funcionario> variosFuncionarios;
	List<Projeto> variosProjetos;
	
	public Empresa() {
		this.variosFuncionarios = new LinkedList<>();
		this.variosProjetos = new LinkedList<>();
	}

	public List<Funcionario> getFuncionarios() {
		return this.variosFuncionarios;
	}
	
	public List<Projeto> getProjetos() {
		return this.variosProjetos;
	}
	
	public void cadastrarFuncionario(Funcionario novoFuncionario) throws FuncionarioJaCadastrado {
		if(this.variosFuncionarios.contains(novoFuncionario))
			throw new FuncionarioJaCadastrado();
		this.variosFuncionarios.add(novoFuncionario);
	}

	public void cadastrarProjeto(Projeto novoProjeto) throws ProjetoJaCadastrado {
		if(this.variosProjetos.contains(novoProjeto))
			throw new ProjetoJaCadastrado();
		this.variosProjetos.add(novoProjeto);
	}
}
