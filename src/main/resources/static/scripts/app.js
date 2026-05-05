// ==========================================
// 1. GESTIÓN DE SESIÓN (LOGIN Y LOGOUT)
// ==========================================

window.cerrarSesion = function() {
    localStorage.removeItem("usuarioLynca");
    window.location.href = "/vistas/publico/login.html";
};

document.addEventListener("DOMContentLoaded", () => {
    // LOGIN
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const emailInput = document.getElementById("email").value;
            const passwordInput = document.getElementById("password").value;

            try {
                const response = await fetch("/auth/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email: emailInput, password: passwordInput })
                });

                if (response.ok) {
                    const usuario = await response.json();
                    localStorage.setItem("usuarioLynca", JSON.stringify(usuario));
                    const rol = usuario.rol.trim().toUpperCase();
                    if (rol === "ADMIN") window.location.href = "/vistas/admin/admin.html";
                    else if (rol === "GESTOR") window.location.href = "/vistas/gestor/dashboard-gestor.html";
                } else if (response.status === 403) {
                    alert("ACCESO DENEGADO: Tu cuenta ha sido bloqueada por un administrador.");
                } else {
                    document.getElementById("errorMsg").style.display = "block";
                }
            } catch (error) { console.error("Error login:", error); }
        });
    }

    // NUEVO: REGISTRO DE ENTIDADES (LO QUE FALTABA)
    const registroForm = document.getElementById("registroEntidadForm");
    if (registroForm) {
        registroForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const datos = {
                nombre: document.getElementById("regNombre").value,
                cif: document.getElementById("regCif").value,
                email: document.getElementById("regEmail").value,
                password: document.getElementById("regPassword").value,
                estado: "PENDIENTE"
            };

            const res = await fetch("/admin/registro-publico", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(datos)
            });

            if (res.ok) {
                alert("Solicitud enviada con éxito. El administrador de Lynca revisará tu centro deportivo.");
                window.location.href = "/vistas/publico/login.html";
            } else {
                alert("Error al enviar la solicitud. Revisa los datos.");
            }
        });
    }

    // CARGA INICIAL PARA EL ADMIN
    if (document.getElementById("listaEntidades")) {
        cargarEntidadesAdmin();
    }
});

// ==========================================
// 2. NAVEGACIÓN Y VISTAS
// ==========================================

window.mostrarVista = function(vista) {
    document.querySelectorAll('.admin-view').forEach(v => v.style.display = 'none');
    const seccion = document.getElementById(`view-${vista}`);
    if (seccion) seccion.style.display = 'block';
    
    document.querySelectorAll('.sidebar-menu a').forEach(a => a.classList.remove('active'));
    if (window.event) window.event.currentTarget.classList.add('active');

    if (vista === 'usuarios') cargarUsuarios();
    if (vista === 'entidades') cargarEntidadesAdmin();
};

// ==========================================
// 3. GESTIÓN DE USUARIOS (DEPORTISTAS)
// ==========================================

async function cargarUsuarios() {
    try {
        const response = await fetch('/admin/usuarios');
        const usuarios = await response.json();
        const tabla = document.getElementById('listaUsuarios');
        if (!tabla) return;
        tabla.innerHTML = '';

        usuarios.forEach(user => {
            const fecha = user.fechaCreacion ? new Date(user.fechaCreacion).toLocaleDateString() : '---';
            const baneado = user.estaBaneado === true;

            tabla.innerHTML += `
                <tr>
                    <td style="font-size: 11px; color: gray;">${user.id.substring(0,8)}...</td>
                    <td>${user.email}</td>
                    <td>${user.nombreCompleto || '---'}</td>
                    <td>${fecha}</td>
                    <td>
                        <span class="badge ${baneado ? 'bg-red' : 'bg-green'}">
                            ${baneado ? 'BANEADO' : 'ACTIVO'}
                        </span>
                    </td>
                    <td>
                        <button onclick="cambiarEstadoBan('${user.id}')" 
                                class="btn-accion ${baneado ? 'btn-activar' : 'btn-banear'}">
                            ${baneado ? '🔓 Activar' : '🚫 Banear'}
                        </button>
                    </td>
                </tr>`;
        });
    } catch (error) { console.error("Error usuarios:", error); }
}

window.cambiarEstadoBan = async function(id) {
    if (confirm("¿Seguro que quieres cambiar el estado de este usuario?")) {
        await fetch(`/admin/usuarios/${id}/toggle-ban`, { method: 'PUT' });
        cargarUsuarios();
    }
};

// ==========================================
// 4. GESTIÓN DE ENTIDADES
// ==========================================

async function cargarEntidadesAdmin() {
    try {
        const res = await fetch("/admin/entidades");
        const datos = await res.json();
        const tabla = document.getElementById("listaEntidades");
        if (!tabla) return;

        tabla.innerHTML = datos.map(e => `
            <tr>
                <td>${e.nombre}</td>
                <td>${e.cif}</td>
                <td><span class="tag ${e.estado.toLowerCase()}">${e.estado}</span></td>
                <td>
                    ${e.estado === 'PENDIENTE' 
                        ? `<button class="btn-edit" style="background:#28a745" onclick="activarEntidad('${e.id}')">Aprobar</button>` 
                        : `<button class="btn-edit" onclick="alert('Gestionando...')">Gestionar</button>`}
                </td>
            </tr>`).join('');
    } catch (e) { console.error("Error entidades:", e); }
}

window.activarEntidad = async function(id) {
    if (!confirm("¿Activar esta entidad?")) return;
    const res = await fetch(`/admin/entidades/${id}/activar`, { method: "PUT" });
    if (res.ok) {
        alert("Entidad activada. Perfil de GESTOR creado.");
        cargarEntidadesAdmin();
    }
};