package br.com.reprografia.prod.common.enumerator;

public enum RoleEnum {
	ROLE_GERAL("ROLE_GERAL"),
	ROLE_TI("ROLE_TI"),
	ROLE_REPROGRAFIA("ROLE_REPROGRAFIA"),
	ROLE_COORDENADOR("ROLE_COORDENADOR"),
	ROLE_PROFESSOR("ROLE_PROFESSOR"),
	ROLE_DIRETOR("ROLE_DIRETOR"),
	ROLE_GESTOR_DE_CUSTOS("ROLE_GESTOR_DE_CUSTOS"),
	ROLE_GERENTE("ROLE_GERENTE");

	private String permissao;

	RoleEnum (String roleName){
		this.permissao = roleName;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

    public static RoleEnum getValueByName(String roleName){
        for(RoleEnum role: RoleEnum.values()){
            if (role.permissao.equalsIgnoreCase(roleName)){
                return role;
            }
        }
        return null;
    }

}
