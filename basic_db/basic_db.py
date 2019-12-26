import yaml
import os

def user_input(data):
    print("Inputting new entry.")
    name = input("Input name:\t")
    author = input("Input author:\t")
    year = input("Input year:\t")
    tags = input("Input tags, seperated by commas:\n").split(",")
    text = input("Input text:\n")
    current_data = dict(
        name = dict(
            author = author,
            year = year,
            tags = tags,
            text = text,
        )
    )
    # Replace name with actual name of string
    current_data[str(name)] = current_data['name']
    del current_data['name']
    data.update(current_data)
    return data

if __name__ == "__main__":
    data = dict()
    with open("basic_db\\basic_db.yaml", "r") as f:
        yaml_data = yaml.load(f, Loader=yaml.FullLoader)
    # Update the yaml_data and prevent None Type Errors
    if yaml_data != None:
        data = yaml_data
    print(repr(data))
    data = user_input(data)
    with open("basic_db\\basic_db.yaml", "w") as f:
        yaml.dump(data, f, sort_keys=True)
