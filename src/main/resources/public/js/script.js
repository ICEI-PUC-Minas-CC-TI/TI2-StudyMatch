const DATA = {
  disciplines: [
    'Cálculo I',
    'Algoritmos e Estruturas de Dados I',
    'Algoritmos e Estruturas de Dados II',
    'Banco de Dados I'
  ],
  objectives: ['Preparar para prova', 'Reforçar a base', 'Aprofundar conteúdo', 'Fazer trabalhos'],
  tasks: [
    ['Revisão de limites e continuidade', 'Retome as propriedades dos limites. Resolva 5 questões e revise os pontos em que teve dúvidas.', '12 nov · 60 min'],
    ['Derivadas e suas aplicações', 'Revise as regras de derivação, a regra da cadeia e problemas de máximos e mínimos.', '13 nov · 60 min'],
    ['A base das integrais', 'Explore primitivas e o teorema fundamental do cálculo. Compare os conceitos com a derivação.', '14 nov · 60 min'],
    ['Integrais indefinidas', 'Pratique substituição e integração por partes. Separe os exercícios que quer discutir com seu grupo.', '15 nov · 60 min'],
    ['Revisão final e simulado', 'Resolva um simulado de integrais e aplicações. Confira suas respostas e revise as principais dificuldades.', '19 nov · 60 min'],
  ],
};

const DISC_BY_ID = {
  1: 'Algoritmos e Estruturas de Dados I',
  2: 'Cálculo I',
  8: 'Algoritmos e Estruturas de Dados II',
  9: 'Banco de Dados I'
};

const ID_BY_DISC = {
  'Algoritmos e Estruturas de Dados I': 1,
  'Cálculo I': 2,
  'Algoritmos e Estruturas de Dados II': 8,
  'Banco de Dados I': 9
};

const ICON_BY_ID = { 1: 'code', 2: 'sigma', 8: 'braces', 9: 'database' };
const MODE_MAP = { online: 'Online', presencial: 'Presencial', hibrido: 'Híbrida' };
const OBJ_MAP = { preparar_prova: 'Preparar para prova', reforcar_base: 'Reforçar a base', aprofundar: 'Aprofundar conteúdo', trabalhos: 'Fazer trabalhos' };

async function getGroups() {
  try {
    const [resGrupos, resPart] = await Promise.all([
      fetch('/grupo/list/1'),
      fetch('/participa/list/1')
    ]);
    if (!resGrupos.ok) return [];
    const dbGroups = await resGrupos.json();
    const parts = resPart.ok ? await resPart.json() : [];

    return dbGroups.map((g, idx) => {
      const groupParts = parts.filter(p => p.idGrupo === g.idGrupo);
      const count = groupParts.length || 1;
      return {
        id: String(g.idGrupo),
        name: g.nome,
        discipline: DISC_BY_ID[g.idDisciplina] || 'Cálculo I',
        mode: MODE_MAP[g.modalidade] || g.modalidade,
        schedule: 'Ter e qui · 19h às 20h',
        members: count,
        limit: g.limiteParticipantes,
        score: 95 - (idx * 3),
        theme: ['green', 'coral', 'blue', 'gold'][idx % 4],
        objective: OBJ_MAP[g.objetivo] || g.objetivo,
        description: g.descricao || 'Grupo de estudos.',
        initials: ['GL', 'AS', 'MC', 'RD'].slice(0, Math.min(count, 3)),
        icon: ICON_BY_ID[g.idDisciplina] || 'book-open'
      };
    });
  } catch (e) {
    return [];
  }
}

async function getGroup(id) {
  const all = await getGroups();
  return all.find(g => String(g.id) === String(id)) || all[0];
}

const $ = (s, r = document) => r.querySelector(s);
const ic = (name, size = 16) => `<i data-lucide="${name}" width="${size}" height="${size}" style="width:${size}px;height:${size}px"></i>`;
const refreshIcons = () => window.lucide && lucide.createIcons();
const norm = s => s.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
const avatar = (i = 'GL', tone = 0, small = false) => `<span class="person-avatar avatar-tone-${tone % 4} ${small ? 'avatar-small' : ''}">${i}</span>`;

const NAV = [
  ['index.html', 'Visão geral', 'layout-grid', 'index'], ['buscar.html', 'Encontrar grupos', 'search', 'buscar'],
  ['grupo.html', 'Meus grupos', 'users', 'grupo'], ['plano.html', 'Planos de estudo', 'sparkles', 'plano'], ['perfil.html', 'Meu perfil', 'user-round', 'perfil'],
];
const brand = `<span class="brand"><span class="brand-icon">${ic('book-open', 22)}</span>study<span class="brand-match">match</span><span class="brand-dot">.</span></span>`;

function shell(page, content) {
  const cur = NAV.find(n => n[3] === page);
  const title = cur ? cur[1] : 'Criar grupo';
  $('#app').innerHTML = `<div class="app-shell">
  <aside class="sidebar" id="sidebar"><a href="index.html" class="brand-link">${brand}</a>
    <div class="workspace"><span class="workspace-icon">${ic('graduation-cap', 19)}</span><div><strong>Seu espaço de estudos</strong><span>Ciência da Computação</span></div></div>
    <div class="nav-label">MEU CAMPUS</div>
    <nav>${NAV.map(n => `<a href="${n[0]}" class="btn btn-ghost nav-item ${n[3] === page ? 'nav-active' : ''}">${ic(n[2], 19)}${n[1]}${n[3] === 'plano' ? '<span class="tiny-ai">IA</span>' : ''}</a>`).join('')}</nav>
    <a href="criar.html" class="btn btn-outline sidebar-create">${ic('plus')}Criar um grupo</a>
    <div class="sidebar-bottom"><div class="sidebar-note"><span class="note-symbol">✳</span><h3>Juntos, a gente<br>vai mais longe.</h3><p>Seu próximo parceiro de estudos pode estar a um match de distância.</p><a href="buscar.html">Encontre sua turma ${ic('arrow-up-right', 15)}</a></div>
    <a href="perfil.html" class="sidebar-user">${avatar()}<div><strong>Gabriel Lima</strong><span>Estudante · 4º semestre</span></div>${ic('chevron-down')}</a></div>
  </aside><div class="menu-backdrop" id="backdrop" hidden></div>
  <div class="main-shell"><header class="topbar"><div class="topbar-left"><button class="btn btn-ghost btn-icon mobile-menu" id="menuBtn" aria-label="Abrir menu">${ic('menu', 20)}</button>
    <span class="breadcrumb">Meu campus <span>/</span> <strong>${title}</strong></span></div>
    <div class="topbar-right"><span class="semester">2026.2 <span>SEMESTRE</span></span>
      <div class="notifications-wrap"><button class="btn btn-ghost btn-icon notification-button" id="notifBtn" aria-label="Notificações">${ic('bell', 19)}<i></i></button>
      <div class="notification-panel" id="notifPanel" hidden><strong>Suas notificações</strong><p>Seu encontro de Cálculo I começa hoje às 19h.</p><span>Há 20 minutos</span><p>O plano de Cálculo I está pronto para consulta.</p><span>Hoje</span></div></div>
      <span class="topbar-divider"></span><a href="perfil.html" aria-label="Meu perfil">${avatar('GL', 0, true)}</a></div></header>
  <main class="page-content"><div id="page">${content}</div><footer class="page-footer"><span>Feito para aprender. Melhor quando é junto.</span><span>StudyMatch © 2026</span></footer></main></div></div>`;
  const sb = $('#sidebar'), bd = $('#backdrop');
  const toggleMenu = open => { sb.classList.toggle('sidebar-open', open); bd.hidden = !open; };
  $('#menuBtn').onclick = () => toggleMenu(!sb.classList.contains('sidebar-open'));
  bd.onclick = () => toggleMenu(false);
  $('#notifBtn').onclick = () => { const p = $('#notifPanel'); p.hidden = !p.hidden; };
  refreshIcons();
}

function groupCard(g) {
  return `<article class="group-card theme-${g.theme}"><div class="group-card-heading"><span class="subject-icon">${ic(g.icon, 23)}</span><span class="match-label"><span>✳</span> ${g.score}% de match</span></div>
  <span class="subject-label">${g.discipline}</span><h3>${g.name}</h3><p class="group-description">${g.description}</p>
  <div class="group-meta"><span>${ic(g.mode === 'Presencial' ? 'map-pin' : 'monitor', 14)} ${g.mode}</span><span>${ic('clock-3', 14)}${g.schedule}</span></div>
  <div class="group-card-footer"><div class="member-info"><div class="avatar-stack">${g.initials.map((i, n) => avatar(i, n, true)).join('')}</div><span>${g.members}/${g.limit} ${ic('users', 13)}</span></div>
  <a class="btn btn-ghost btn-icon" href="grupo.html?id=${g.id}" aria-label="Ver ${g.name}">${ic('arrow-up-right', 19)}</a></div></article>`;
}

const pages = {
  async index() {
    const groups = await getGroups();
    shell('index', `<div class="greeting-row"><div><span class="eyebrow">UM NOVO DIA PARA APRENDER</span><h1>Bom dia, Gabriel <span>☀️</span></h1><p class="subheading">Que tal dar o próximo passo nos seus estudos?</p></div><span class="date-display">${ic('calendar-days', 15)}Quinta-feira, 15 de outubro</span></div>
    <section class="home-hero"><img src="assets/study-together.jpg" alt="Estudantes compartilhando conhecimentos na biblioteca universitária"><div class="hero-copy"><span class="hero-kicker">${ic('leaf', 13)} CONEXÕES QUE FAZEM A DIFERENÇA</span><h2>Seu próximo match.<br>Sua próxima conquista.</h2><p>Encontre pessoas que estudam o que você estuda.<br>Compartilhe dúvidas. Multiplique descobertas.</p><a class="btn btn-outline hero-button" href="buscar.html">Encontrar meu grupo ${ic('arrow-right', 14)}</a></div><span class="hero-caption"><i></i> Aprender é melhor em boa companhia.</span></section>
    <section class="stats-row">
      <div class="stat-item"><span class="stat-icon">${ic('users', 20)}</span><div class="stat-text"><span>Grupos disponíveis</span><div class="stat-value">${groups.length} <small>grupos ativos</small></div><p>Conhecimento compartilhado</p></div></div>
      <div class="stat-item"><span class="stat-icon blue">${ic('clock-3', 20)}</span><div class="stat-text"><span>Próximo encontro</span><div class="stat-value">19h <small>hoje</small></div><p>Cálculo I · Online</p></div></div>
      <div class="stat-item"><span class="stat-icon coral">${ic('calendar-days', 20)}</span><div class="stat-text"><span>Próxima prova</span><div class="stat-value">20 nov <small>em breve</small></div><p>Cálculo I · Você está no caminho</p></div></div></section>
    <div class="dashboard-grid"><div>
      <section><div class="section-heading"><div><h2>Encontre sua turma</h2><p>Grupos que combinam com você e seu jeito de estudar.</p></div><a href="buscar.html">Ver todos ${ic('arrow-up-right', 14)}</a></div><div class="group-grid">${groups.slice(0, 2).map(groupCard).join('')}</div></section>
      <section class="your-groups"><div class="section-heading"><h2>Já estamos juntos</h2><a href="buscar.html">Meus grupos ${ic('arrow-up-right', 14)}</a></div>
      ${groups.slice(0, 3).map((g, i) => `<div class="group-list-row theme-${g.theme}"><span class="subject-icon">${ic(g.icon, 18)}</span><div><h3>${g.name}</h3><p>${g.schedule} · ${g.mode}</p></div><span class="group-status">${i === 0 ? 'Encontro hoje' : 'Você participa'}</span><a href="grupo.html?id=${g.id}" aria-label="Abrir ${g.name}">${ic('arrow-up-right', 17)}</a></div>`).join('')}</section></div>
    <aside class="dashboard-agenda"><section><h2 class="agenda-heading">Sua semana</h2><p class="agenda-date">Novembro de 2026</p><div class="week-strip" id="week"></div><div id="agendaEvent"></div>
      <div class="agenda-event"><span class="event-time">20 NOV · SEXTA-FEIRA</span><h3>Prova de Cálculo I</h3><p>${ic('calendar-days', 13)} Integrais e aplicações</p></div></section>
      <section class="plan-mini"><span class="plan-mini-title">${ic('sparkles', 14)} SEU PLANO, SEU RITMO</span><h3>Um pouco a cada dia.</h3><p>Seu plano de Cálculo I está te<br>levando mais longe.</p><div class="plan-progress"><span>Seu progresso</span><strong>60%</strong></div><progress value="60" max="100"></progress><div class="detail-line" style="margin-top:12px">${ic('check', 13)}<span>3 de 5 etapas concluídas</span></div><a class="plan-link" href="plano.html">Continuar meu plano ${ic('arrow-right', 15)}</a></section></aside></div>`);
    let day = 15;
    const firstId = groups[0] ? groups[0].id : 1;
    const draw = () => {
      $('#week').innerHTML = ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'].map((l, i) => `<button class="btn btn-ghost day-button ${day === 12 + i ? 'day-active' : ''}" data-day="${12 + i}"><span>${l}</span><strong>${12 + i}</strong></button>`).join('');
      $('#agendaEvent').innerHTML = [13, 15].includes(day)
        ? `<div class="agenda-event"><span class="event-time">19:00 — 20:00</span><h3>${groups[0] ? groups[0].name : 'Encontro de Estudos'}</h3><p>${ic('video', 13)} Encontro online · Cálculo I</p><div class="event-bottom"><div class="avatar-stack">${avatar('GL', 0, true)}${avatar('AS', 1, true)}${avatar('MC', 2, true)}</div><a href="grupo.html?id=${firstId}">Ver encontro ↗</a></div></div>`
        : `<div class="agenda-event"><span class="event-time">UM TEMPO PARA VOCÊ</span><h3>Nenhum encontro marcado</h3><p>Um bom dia para revisar seu plano.</p></div>`;
      document.querySelectorAll('[data-day]').forEach(b => b.onclick = () => { day = +b.dataset.day; draw(); });
      refreshIcons();
    };
    draw();
  },

  async buscar() {
    const groups = await getGroups();
    shell('buscar', `<div class="page-heading"><div><span class="eyebrow">BOAS CONEXÕES COMEÇAM AQUI</span><h1>Encontre sua turma.</h1><p class="subheading">Mesma disciplina, novos pontos de vista.</p></div><a class="btn" href="criar.html">${ic('plus')}Criar grupo</a></div>
    <div class="search-field">${ic('search', 18)}<input class="field" id="q" type="search" aria-label="Buscar grupos" placeholder="Busque por disciplina ou nome do grupo..."></div>
    <div class="filters"><div class="filter-tabs" id="modes"></div>
      <select class="field" id="disc" aria-label="Filtrar por disciplina"><option>Todas as disciplinas</option>${DATA.disciplines.map(d => `<option>${d}</option>`).join('')}</select>
      <select class="field" id="sort" aria-label="Ordenar grupos"><option value="match">Maior compatibilidade</option><option value="spots">Mais vagas disponíveis</option></select></div>
    <p class="results-count" id="count"></p><div class="group-grid search-grid" id="results"></div><div id="empty"></div>`);
    let mode = 'Todas';
    const draw = () => {
      $('#modes').innerHTML = ['Todas', 'Online', 'Presencial', 'Híbrida'].map(m => `<button class="btn btn-ghost ${m === mode ? 'selected' : ''}" data-m="${m}">${m}</button>`).join('');
      document.querySelectorAll('[data-m]').forEach(b => b.onclick = () => { mode = b.dataset.m; draw(); });
      const q = norm($('#q').value), d = $('#disc').value, s = $('#sort').value;
      const r = groups.filter(g => norm(g.name + ' ' + g.discipline).includes(q) && (mode === 'Todas' || g.mode === mode) && (d === 'Todas as disciplinas' || g.discipline === d))
        .sort((a, b) => s === 'match' ? b.score - a.score : (b.limit - b.members) - (a.limit - a.members));
      $('#count').innerHTML = `<strong>${r.length} grupos</strong> encontrados`;
      $('#results').innerHTML = r.map(groupCard).join('');
      $('#empty').innerHTML = r.length ? '' : `<div class="empty-state">${ic('sliders-horizontal', 28)}<h3>Nenhum grupo encontrado.</h3><p>Experimente outra disciplina, modalidade ou crie um novo grupo.</p><button class="btn btn-link" id="clear">Limpar filtros</button></div>`;
      if (!r.length) $('#clear').onclick = () => { $('#q').value = ''; $('#disc').value = 'Todas as disciplinas'; mode = 'Todas'; draw(); };
      refreshIcons();
    };
    ['#q', '#disc', '#sort'].forEach(s => $(s).oninput = draw);
    draw();
  },

  async criar() {
    shell('criar', `<div class="page-heading"><div><span class="eyebrow">UMA IDEIA. VÁRIAS POSSIBILIDADES.</span><h1>Crie sua próxima conexão.</h1><p class="subheading">Todo grande grupo começa com alguém. Hoje, esse alguém é você.</p></div></div><div id="notice"></div>
    <div class="content-columns"><form id="form"><section class="content-section"><h2>01 · Sobre o grupo</h2><div class="form-grid">
      <label class="form-label full">Nome do grupo<input class="field" id="name" required maxlength="80" placeholder="Ex.: Cálculo sem complicação"></label>
      <label class="form-label full">Descrição<textarea class="field" id="desc" required placeholder="O que vocês querem aprender juntos?"></textarea></label>
      <label class="form-label">Disciplina<select class="field" id="disc">${DATA.disciplines.map(d => `<option>${d}</option>`).join('')}</select></label>
      <label class="form-label">Objetivo de estudo<select class="field" id="obj">${DATA.objectives.map(o => `<option>${o}</option>`).join('')}</select></label></div></section>
    <section class="content-section"><h2>02 · Do seu jeito</h2><div class="form-grid">
      <label class="form-label">Modalidade<select class="field" id="mode"><option>Online</option><option>Presencial</option><option>Híbrida</option></select></label>
      <label class="form-label">Limite de participantes<input class="field" id="limit" type="number" required min="2" value="6"></label></div></section>
    <section class="content-section"><h2>03 · Hora de se encontrar</h2><div class="schedule-picker" id="slots"></div></section>
    <div class="form-actions"><a class="btn btn-ghost" href="buscar.html">Cancelar</a><button type="submit">${ic('save')}Salvar</button></div></form>
    <aside><div class="preview-title">PRÉVIA DO SEU GRUPO</div><div class="preview-card" id="preview"></div><p class="form-note">Você será o criador e o primeiro membro do grupo. Como criador, só poderá excluir o grupo, não sair dele.</p></aside></div>`);
    const all = ['Seg · 14h–15h', 'Ter · 19h–20h', 'Qua · 20h–21h', 'Qui · 19h–20h', 'Sex · 14h–15h'];
    let slots = [];
    const draw = () => {
      $('#slots').innerHTML = all.map(s => `<button type="button" class="btn btn-outline ${slots.includes(s) ? 'selected' : ''}" data-s="${s}">${slots.includes(s) ? ic('check') : ''}${s}</button>`).join('');
      document.querySelectorAll('[data-s]').forEach(b => b.onclick = () => { const s = b.dataset.s; slots = slots.includes(s) ? slots.filter(x => x !== s) : [...slots, s]; draw(); });
      $('#preview').innerHTML = `<span class="subject-icon">${ic('book-open', 20)}</span><h3>${$('#name').value || 'Seu próximo grupo'}</h3><p>${$('#desc').value || 'Um espaço para compartilhar dúvidas e construir conhecimento.'}</p>
      <div class="detail-line">${ic('book-open', 15)}${$('#disc').value}</div><div class="detail-line">${ic('monitor', 15)}${$('#mode').value}</div><div class="detail-line">${ic('users', 15)}1 de ${$('#limit').value || 2} participantes</div>${slots.map(s => `<div class="detail-line">${ic('clock-3', 15)}${s}</div>`).join('')}`;
      refreshIcons();
    };
    $('#form').addEventListener('input', draw);
    $('#form').onsubmit = async e => {
      e.preventDefault();
      const objMap = { 'Preparar para prova': 'preparar_prova', 'Reforçar a base': 'reforcar_base', 'Aprofundar conteúdo': 'aprofundar', 'Fazer trabalhos': 'trabalhos' };
      const modeMap = { 'Online': 'online', 'Presencial': 'presencial', 'Híbrida': 'hibrido' };

      const params = new URLSearchParams({
        nome: $('#name').value,
        descricao: $('#desc').value,
        idDisciplina: ID_BY_DISC[$('#disc').value] || 2,
        objetivo: objMap[$('#obj').value] || 'preparar_prova',
        modalidade: modeMap[$('#mode').value] || 'online',
        limiteParticipantes: $('#limit').value,
        cpfCriador: localStorage.getItem('cpfLogado') || '12345678901'
      });

      try {
        const res = await fetch('/grupo/insert', { method: 'POST', body: params });
        const data = await res.json();
        if (res.ok) {
          $('#notice').innerHTML = `<div class="notice">Grupo salvo com sucesso!</div>`;
          setTimeout(() => location.href = 'buscar.html', 1000);
        } else {
          $('#notice').innerHTML = `<div class="notice" style="background:var(--coral-soft);color:var(--coral)">Erro: ${data.erro}</div>`;
        }
      } catch (err) {
        $('#notice').innerHTML = `<div class="notice" style="background:var(--coral-soft);color:var(--coral)">Erro de conexão com o servidor.</div>`;
      }
    };
    draw();
  },

  async grupo() {
    const id = new URLSearchParams(location.search).get('id');
    const g = await getGroup(id);
    if (!g) {
      shell('grupo', `<div class="empty-state"><h3>Nenhum grupo cadastrado.</h3><a class="btn" href="criar.html">Criar grupo</a></div>`);
      return;
    }
    const names = ['Gabriel Lima', 'Anne Nascimento', 'Mateus Caldeira', 'Raphael Dantas', 'Mariana Costa', 'Lucas Oliveira'].slice(0, g.members);
    shell('grupo', `<a href="buscar.html" class="back-link">${ic('arrow-left', 14)}Todos os grupos</a><div id="notice"></div>
    <section class="group-banner theme-${g.theme}"><span class="subject-icon">${ic(g.icon, 22)}</span><h1>${g.name}</h1><p>${g.description}</p>
    <div class="tag-row"><span class="tag">${ic('book-open', 13)}${g.discipline}</span><span class="tag">${ic('monitor', 13)}${g.mode}</span><span class="tag">${g.objective}</span><span class="tag">${g.score}% de match</span></div></section>
    <div class="content-columns"><div><section class="content-section"><h2>Gente que aprende com você</h2>
      ${names.map((n, i) => `<div class="member-row">${avatar(n.split(' ').map(x => x[0]).join(''), i)}<div><strong>${n}</strong><p>Ciência da Computação · 4º semestre</p></div><span>${i === 0 ? 'Criador(a)' : 'Membro'}</span></div>`).join('')}</section>
      <section class="content-section"><h2>Nosso objetivo</h2><p class="subheading">${g.objective}. Um encontro para compartilhar dúvidas, revisar conceitos e resolver exercícios em boa companhia.</p></section></div>
    <aside><div class="preview-card"><span class="eyebrow">PRÓXIMO ENCONTRO</span><h3>Uma hora para aprender.</h3>
      <div class="detail-line">${ic('calendar-days', 16)}Quinta-feira, 15 de outubro</div><div class="detail-line">${ic('clock-3', 16)}${g.schedule}</div><div class="detail-line">${ic('monitor', 16)}${g.mode}</div><div class="detail-line">${ic('users', 16)}${g.members} de ${g.limit} participantes</div>
      <button class="btn w-full" id="joinBtn" style="margin-bottom:8px">Participar do grupo</button>
      <button class="btn btn-outline w-full" id="delBtn" style="margin-bottom:8px;color:var(--coral)">Excluir grupo</button>
      <a class="btn btn-outline w-full" href="plano.html">Consultar plano de estudo</a></div></aside></div>`);

    $('#joinBtn').onclick = async () => {
      const cpf = localStorage.getItem('cpfLogado') || '12345678901';
      const res = await fetch('/participa/insert', { method: 'POST', body: new URLSearchParams({ cpf, idGrupo: g.id }) });
      const data = await res.json();
      $('#notice').innerHTML = `<div class="notice">${res.ok ? 'Você entrou no grupo!' : data.erro}</div>`;
      if (res.ok) setTimeout(() => location.reload(), 800);
    };
    $('#delBtn').onclick = async () => {
      const res = await fetch(`/grupo/delete/${g.id}`);
      if (res.ok) location.href = 'buscar.html';
    };
  },

  async plano() {
    shell('plano', `<div class="page-heading"><div><span class="eyebrow">UM POUCO A CADA DIA</span><h1>Seu plano. Seu ritmo.</h1><p class="subheading">Cada etapa é um passo a mais em direção à sua conquista.</p></div><span class="tag">${ic('sparkles', 14)}Gerado com IA</span></div><div id="notice"></div>
    <div class="study-header">${ic('sparkles', 24)}<div><span class="eyebrow">CÁLCULO I</span><h2>Do conceito à confiança.</h2><p id="planDesc">Foco em integrais e aplicações, com uma hora de estudo por dia até a prova.</p></div></div>
    <div class="content-columns"><section>
      <form id="planForm" class="content-section" style="padding-top:0;margin-bottom:20px">
        <h2>Novo plano de estudos</h2>
        <div class="form-grid">
          <label class="form-label">Data da prova<input class="field" id="dataProva" type="date" value="2026-11-20" required></label>
          <label class="form-label">Tempo disponível por dia<input class="field" id="tempoDisp" value="60 minutos" required></label>
          <label class="form-label full">Principais dificuldades<input class="field" id="dificuldades" placeholder="Ex.: Integração por partes e limites" required></label>
        </div>
        <div class="form-actions"><button type="submit">${ic('save')}Salvar</button></div>
      </form>
      <div class="section-heading"><h2>Seu caminho de estudos</h2><span class="subheading">5 etapas</span></div><div id="tasks"></div></section>
    <aside><div class="study-progress"><h3>Você está no caminho.</h3><div class="progress-number" id="pct"></div><p id="cnt"></p><progress id="bar" max="5"></progress>
      <div class="content-section"><div class="detail-line">${ic('calendar-days', 16)}Prova · 20 de novembro</div><div class="detail-line">${ic('clock-3', 16)}60 minutos por dia</div><div class="detail-line">${ic('book-open', 16)}Cálculo I</div><p>Principais dificuldades: integração por partes e aplicação de integrais.</p></div></div></aside></div>`);
    let done = [0, 1, 2];
    const draw = () => {
      $('#tasks').innerHTML = DATA.tasks.map((t, i) => `<label class="study-task ${done.includes(i) ? 'task-done' : ''}"><input type="checkbox" data-t="${i}" ${done.includes(i) ? 'checked' : ''}><div><h3>${t[0]}</h3><p>${t[1]}</p><span>${t[2]}</span></div></label>`).join('');
      document.querySelectorAll('[data-t]').forEach(c => c.onchange = () => { const i = +c.dataset.t; done = done.includes(i) ? done.filter(d => d !== i) : [...done, i]; draw(); });
      $('#pct').textContent = done.length * 20 + '%'; $('#cnt').textContent = `${done.length} de 5 etapas concluídas`; $('#bar').value = done.length;
      refreshIcons();
    };
    $('#planForm').onsubmit = async e => {
      e.preventDefault();
      const params = new URLSearchParams({
        cpf: localStorage.getItem('cpfLogado') || '12345678901',
        idDisciplina: 2,
        dataProva: $('#dataProva').value,
        tempoDisponivel: $('#tempoDisp').value,
        dificuldades: $('#dificuldades').value,
        conteudo: 'Plano personalizado para: ' + $('#dificuldades').value
      });
      const res = await fetch('/planoestudo/insert', { method: 'POST', body: params });
      const data = await res.json();
      if (res.ok) {
        $('#planDesc').textContent = data.conteudo;
        $('#notice').innerHTML = `<div class="notice">Plano salvo com sucesso!</div>`;
      } else {
        $('#notice').innerHTML = `<div class="notice" style="background:var(--coral-soft);color:var(--coral)">Erro: ${data.erro}</div>`;
      }
    };
    draw();
  },

  async perfil() {
    shell('perfil', `<div class="page-heading"><div><span class="eyebrow">SEU JEITO DE APRENDER</span><h1>Um perfil, muitas conexões.</h1><p class="subheading">Seu ponto de partida para encontrar pessoas com objetivos em comum.</p></div></div>
    <div class="profile-summary">${avatar()}<div><h2>Gabriel Lima</h2><p>Ciência da Computação · 4º semestre</p></div></div><div id="notice"></div>
    <form id="form"><section class="content-section"><h2>Sobre você</h2><div class="form-grid">
      <label class="form-label">Nome completo<input class="field" id="pNome" value="Gabriel Lima" required></label>
      <label class="form-label">E-mail<input class="field" id="pEmail" type="email" value="gabriel.lima@sga.pucminas.br" required></label>
      <label class="form-label">CPF (11 números)<input class="field" id="pCpf" value="12345678901" required></label>
      <label class="form-label">Curso<input class="field" id="pCurso" value="Ciência da Computação" required></label>
      <label class="form-label">Semestre atual<select class="field" id="pSemestre">${Array.from({ length: 12 }, (_, i) => `<option value="${i + 1}" ${i === 3 ? 'selected' : ''}>${i + 1}º semestre</option>`).join('')}</select></label>
      <label class="form-label">Modalidade preferida<select class="field" id="pMod"><option>Online</option><option>Presencial</option><option>Híbrida</option></select></label></div></section>
    <section class="content-section"><h2>Suas disciplinas</h2>${DATA.disciplines.slice(0, 3).map((d, i) => `<div class="enrollment"><span class="subject-icon">${ic('book-open', 20)}</span><div><strong>${d}</strong><p>${['Preparar para prova', 'Reforçar a base', 'Fazer trabalhos'][i]}</p></div><select class="field" aria-label="Nível em ${d}"><option ${i === 1 ? 'selected' : ''}>Básico</option><option ${i !== 1 ? 'selected' : ''}>Intermediário</option><option>Avançado</option></select></div>`).join('')}</section>
    <section class="content-section"><h2>Quando você está disponível?</h2><div class="availability" id="grid"></div></section>
    <div class="form-actions"><button type="submit">${ic('save')}Salvar</button></div></form>`);
    let slots = ['19-1', '19-3', '20-2'];
    const days = ['Seg', 'Ter', 'Qua', 'Qui', 'Sex'];
    const draw = () => {
      $('#grid').innerHTML = '<span></span>' + days.map(d => `<span class="day-label">${d}</span>`).join('') + [14, 15, 16, 17, 18, 19, 20].map(h => `<span class="hour-label">${h}h–${h + 1}h</span>` + days.map((d, i) => { const k = `${h}-${i}`, on = slots.includes(k); return `<button type="button" class="btn btn-ghost slot ${on ? 'slot-selected' : ''}" data-k="${k}" aria-pressed="${on}" aria-label="${d}, ${h}h às ${h + 1}h">${on ? ic('check', 13) : ''}</button>`; }).join('')).join('');
      document.querySelectorAll('[data-k]').forEach(b => b.onclick = () => { const k = b.dataset.k; slots = slots.includes(k) ? slots.filter(x => x !== k) : [...slots, k]; draw(); });
      refreshIcons();
    };
    $('#form').onsubmit = async e => {
      e.preventDefault();
      const cpf = $('#pCpf').value.replace(/\D/g, '');
      const modeMap = { 'Online': 'online', 'Presencial': 'presencial', 'Híbrida': 'hibrido' };
      const params = new URLSearchParams({
        cpf,
        nome: $('#pNome').value,
        email: $('#pEmail').value,
        senha: '123',
        curso: $('#pCurso').value,
        semestre: $('#pSemestre').value,
        modalidadePreferida: modeMap[$('#pMod').value] || 'online'
      });

      let res = await fetch('/estudante/insert', { method: 'POST', body: params });
      if (!res.ok) {
        res = await fetch(`/estudante/update/${cpf}`, { method: 'POST', body: params });
      }
      const data = await res.json();
      if (res.ok) {
        localStorage.setItem('cpfLogado', cpf);
        $('#notice').innerHTML = '<div class="notice">Alterações salvas com sucesso!</div>';
      } else {
        $('#notice').innerHTML = `<div class="notice" style="background:var(--coral-soft);color:var(--coral)">Erro: ${data.erro}</div>`;
      }
    };
    draw();
  },
};
pages[document.body.dataset.page]();