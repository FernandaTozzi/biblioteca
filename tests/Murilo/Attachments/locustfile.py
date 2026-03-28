from locust import HttpUser, between, task
from faker import Faker

fake = Faker()

class WebsiteUser(HttpUser):
    wait_time = between(1,3)

    host = "http://127.0.0.1:8080"

    def on_start(self):
        response = self.client.post(
            "/auth",
            json = {
                "email" : "samuca@gmail.com",
                "senha" : "12345"
            }
        )

        if response.status_code == 200:
            token = response.json().get("token")

            self.headers = {
                "Authorization" : f"Bearer {token}"
            }
            print(self.headers)
        else:
            print("Erro ao autenticar:", response.text)
            self.headers = {}

    @task
    def index(self):
        self.client.post("/livro", headers = self.headers, json={
                "nm_livro": fake.sentence(nb_words=10),
                "cd_bibliotecario": 1,
                "categoria": 1,
                "cd_autor": 1,
                "sn_locado": "N"
        })
    
    #@task
    #def index(self):
    #    self.client.get("/livro", headers = self.headers)