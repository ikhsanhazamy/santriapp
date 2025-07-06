function applyMode(mode) {
    const body = document.body;
    body.classList.remove("light-mode", "dark-mode");
    body.classList.add(`${mode}-mode`);

    const btn = document.getElementById("modeToggleBtn");
    if (btn) {
        if (mode === "dark") {
            btn.innerHTML = "☀️ Mode";
            btn.classList.remove("btn-outline-dark");
            btn.classList.add("btn-outline-light");
        } else {
            btn.innerHTML = "🌙 Mode";
            btn.classList.remove("btn-outline-light");
            btn.classList.add("btn-outline-dark");
        }
    }
}

function toggleMode() {
    const body = document.body;
    const isDark = body.classList.contains("dark-mode");
    const newMode = isDark ? "light" : "dark";
    applyMode(newMode);
    localStorage.setItem("theme", newMode);
}

window.addEventListener("DOMContentLoaded", () => {
    const savedMode = localStorage.getItem("theme") || "light";
    applyMode(savedMode);
});
