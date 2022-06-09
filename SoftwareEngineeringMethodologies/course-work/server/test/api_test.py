import pytest
from main.app import app
from test.test_data import *


@pytest.fixture
def client():
    return app.test_client()


def test_condition_generations(client):
    response = client.get("/conditions")
    assert response.status_code == 200
    assert "horizontal_space_numbers" in response.json.keys()
    assert "vertical_space_numbers" in response.json.keys()


def test_ship_placement_positive1(client):
    response = client.post("/ship-placements", json=valid_condition1)
    assert response.status_code == 200
    assert response.json == solution_for_valid_condition


def test_ship_placement_positive2(client):
    response = client.post("/ship-placements", json=valid_condition2)
    assert response.status_code == 200


def test_ship_placement_negative1(client):
    response = client.post("/ship-placements", json=invalid_condition1)
    assert response.status_code == 200
    assert response.json == []


def test_ship_placement_negative2(client):
    response = client.post("/ship-placements", json=invalid_condition2)
    assert response.status_code == 400
    assert response.text == "Conditions are missed!"


def test_ship_placement_negative3(client):
    response = client.post("/ship-placements", json={})
    assert response.status_code == 400
    assert response.text == "Conditions are missed!"
