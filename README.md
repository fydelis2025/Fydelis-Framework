# Fydelis Framework — Ecossistema Qt/C++ para NetBeans

Suporte nativo ao desenvolvimento Qt e C++ no Apache NetBeans: migração automática Qt5→Qt6, validação de código, assistentes de projeto e ferramentas integradas.

---

## 🚀 Funcionalidades

- ✅ **Migração Qt5 → Qt6** — Detecção e sugestão automática de alterações de API
- ✅ **Validação de Código** — Verificação em tempo real de regras e padrões Qt
- ✅ **Integração Nativa** — Menus, painéis e ações diretamente na interface do NetBeans
- ✅ **Compatível** — NetBeans 18+, JDK 26+
- ✅ **Licença MIT** — Uso livre e aberto

---

## 📦 Instalação

### Pré-requisitos
- Apache NetBeans 31+
- JDK 26 (`jdk-26.0.2.1` ou superior)

### Passo a Passo
1. Baixe o arquivo `.nbm` da página de [Releases](../../releases)
2. No NetBeans: **Ferramentas → Plugins → Baixados → Adicionar Plugin**
3. Selecione o arquivo → Instalar → Reiniciar
4. Acesse: **Ferramentas → Fydelis: Status do Sistema** ✅

---

## 🔧 Desenvolvimento

```bash
# Clonar
git clone https://github.com/seuusuario/fydelis-netbeans-framework.git
cd fydelis-netbeans-framework

# Abrir no NetBeans
# Configurar JDK: nbm.properties → javac.source=26 / javac.target=26
# Limpar e Construir → Criar arquivo de distribuição NBM
