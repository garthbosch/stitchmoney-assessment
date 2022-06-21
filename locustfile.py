import json

from locust import HttpUser, task, between


class QuickstartUser(HttpUser):
    wait_time = between(1, 5)

    @task(1)
    def api_page(self):
        payload = {
            "id": 1,
            "todo": "testing",
            "status": "inprogress"
        }

        headers = {'content-type': 'application/json'}

        response = self.client.post("http://localhost:8080/todos", data=json.dumps(payload), headers=headers)
