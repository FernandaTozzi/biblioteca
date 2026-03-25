from locust import HttpUser, task, between
import random

class ProfessorUser(HttpUser):
    wait_time = between(1, 3)

    host = "http://127.0.0.1:8080"

    def on_start(self):
        response = self.client.post(
            "/auth",
            json={
                "email": "samuca@gmail.com",
                "senha": "12345"
            }
        )

        if response.status_code == 200:
            token = response.json().get("token")

            self.headers = {
                "Authorization": f"Bearer {token}"
            }
            print(self.headers)
        else:
            print("Erro ao autenticar:", response.text)
            self.headers = {}

    @task
    def criar_professor(self):
        self.client.post(
            "/professor",
            headers=self.headers,
            json={
                "nome": f"Professor {random.randint(1,10000)}",
                "email": f"prof{random.randint(1,10000)}@teste.com"
            }
        )

    # @task
    # def buscar_professor(self):
    #     self.client.get("/professor", headers=self.headers)