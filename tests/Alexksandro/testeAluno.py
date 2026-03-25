from locust import HttpUser, task, between
import random

class AlunoUser(HttpUser):
    wait_time = between(1, 2)
    host = "http://localhost:8080"

    def on_start(self):
        self.aluno_id = random.randint(1, 10000)
        
        response = self.client.post("/auth", json={
            "email": "samuca@gmail.com",
            "senha": "12345"
        })
        
        if response.status_code == 200:
            token = response.json().get("token")

            self.headers = {
                "Authorization": f"Bearer {token}"
            }
        else:
            print("Erro ao autenticar:", response.text)
            self.headers = {}

    @task(2)  
    def criar_aluno(self):
        self.client.post(
            "/alunos",
            headers=self.headers,
            name="POST /alunos",  
            json={
                "nm_aluno": "Aluno Teste",
                "dt_nascimento": "2000-01-01T00:00:00",
                "tp_sexo": "M",
                "ds_email": "teste@email.com"
            }
        )

    @task(1)
    def listar_alunos(self):
        self.client.get(
            "/alunos",
            headers=self.headers,
            name="GET /alunos" 
        )