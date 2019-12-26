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

def search_db(data, command):
    target = input("Input query target:\n")
    if (command == "NAME"):
        try:
            found_data = data[target]
            print("Name:\t{name}".format(name=target))
            print_yaml(found_data)
        except KeyError:
            print("Cannot find appropriate name")
            return

def print_yaml(yaml):
    """ Prints a single YAML formatted dict. Remeber to print name first as the name is lost before entering."""
    print("Author:\t{author}".format(author=yaml['author']))
    print("Year:\t{year}".format(years=yaml['year']))
    print("Tags:\t{tags}".format(tags=yaml['tags']))
    print("Text:\n{text}".format(text=yaml['text']))

if __name__ == "__main__":
    data = load_data()
    # Create an empty dict in case there is no yaml_data
    if data == None:
        data = dict()
    
    conditional = True
    valid_search = ["AUTHOR", "NAME", "TAGS", "YEAR"]
    # Main loop
    while conditional:
        command = input("> ").upper()
        if (command == "HELP"):
            print("INPUT\t-\tInput new database entry\nHELP\t-\tDisplay this help page\nQUIT\t-\tLeave program\nSEARCH\t-\tPerform a query on the database")
        elif (command == "INPUT"):
            user_input(data)
        elif (command == "QUIT"):
            print("Saving Data\n")
            write_data(data)
            conditional = False
        elif (command == "SEARCH"):
            search = input("").upper()
        else:
            print("Invalid command.")