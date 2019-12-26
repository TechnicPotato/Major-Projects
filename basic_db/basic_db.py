import yaml
import os

def load_data():
    """ Opens yaml file and returns data as dict"""
    with open("basic_db\\basic_db.yaml", "r") as f:
        yaml_data = yaml.load(f, Loader=yaml.FullLoader)
    if yaml_data != None:
        return yaml_data
    return None

def write_data(data):
    """ Writes data to a yaml file"""
    with open("basic_db\\basic_db.yaml", "w") as f:
        yaml.dump(data, f, sort_keys=True)


def user_input(data):
    """ Takes user input and adds to YAML file"""
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
    # Create an empty dict in case there is no yaml_data
    data = dict()
    
