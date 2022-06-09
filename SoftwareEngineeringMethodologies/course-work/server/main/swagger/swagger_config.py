swagger_template = dict(
    info={
        'title': 'API for task10',
        'version': '1.0',
        'description': 'API contains /conditions endpoint and /ship-placements',
    },
    host="127.0.0.1:5000"
)
swagger_config = {
    "headers": [],
    "specs": [
        {
            "endpoint": 'api-spec',
            "route": '/api-spec',
            "rule_filter": lambda rule: True,
            "model_filter": lambda tag: True,
        }
    ],
    "static_url_path": "/flasgger_static",
    "swagger_ui": True,
    "specs_route": "/swagger/"
}
