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
<<<<<<< HEAD
    data = load_data()
    # Create an empty dict in case there is no yaml_data
    if data == None:
        data = dict()
    
    conditional = True
    # Main loop
    while conditional:
        command = input("> ")
        if (command == "HELP"):
            print("HELP\nINPUT\t-\tInput new database entry\nQUIT\t-\tLeave program\n")
        elif (command == "INPUT"):
            user_input(data)
        elif (command == "QUIT"):
            print("Saving Data\n")
            write_data(data)
            conditional = False
=======
    # Create an empty dict in case there is no yaml_data
    data = dict()
    
>>>>>>> b7fa4e81e2e62e3f361150ec89cf2582ead2aa3a
