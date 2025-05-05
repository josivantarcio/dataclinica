-- Inserir usuário administrador (senha: admin123)
INSERT INTO usuarios (nome, email, senha, tipo)
VALUES (
    'Administrador',
    'admin@dataclin.com',
    '$2a$10$X7UrH5UxX5UxX5UxX5UxX.5UxX5UxX5UxX5UxX5UxX5UxX5UxX5UxX',
    'ADMIN'
)
ON CONFLICT (email) DO NOTHING; 