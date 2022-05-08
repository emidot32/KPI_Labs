"""API for task10"""
from flask import Flask, Response, jsonify, request
from flasgger import Swagger, swag_from
from main.place_ships import get_all_appropriate_ships
from main.swagger.swagger_config import swagger_template, swagger_config
import main.conditions_generation

app = Flask(__name__)
swagger = Swagger(app, template=swagger_template, config=swagger_config)


@swag_from(r"swagger\conditions.yml", methods=['GET'])
@app.route('/conditions', methods=["GET"])
def get_conditions():
    """Conditions generation endpoint"""
    return main.conditions_generation.get_conditions()


@swag_from(r"swagger\ship_placements.yml", methods=['POST'])
@app.route('/ship-placements', methods=["POST"])
def place_ships():
    """Ship placement endpoint"""
    body: dict = request.get_json()
    if not body:
        return Response("Conditions are missed!", status=400)
    if "horizontal_space_numbers" not in body.keys() or "vertical_space_numbers" not in body.keys():
        return Response("Conditions are missed!", status=400)
    horizontal_space_numbers: dict = body["horizontal_space_numbers"]
    vertical_space_numbers: dict = body["vertical_space_numbers"]

    seas = get_all_appropriate_ships(
        {int(k): v for k, v in horizontal_space_numbers.items()},
        {int(k): v for k, v in vertical_space_numbers.items()}
    )
    return jsonify(seas)


if __name__ == '__main__':
    app.run(debug=True)
