//Barf Farming script by IWAM
script "barf_farm.ash";
//notify idiotwithamatch;


//Borrowed off gausie
void use_bastille_battalion(int desired_stat, int desired_item, int desired_buff, int desired_potion)
{
	if (item_amount($item[Bastille Battalion control rig]) == 0) return;

	int [4] desired_state;
	int [4] current_state;

	desired_state[0] = desired_stat;
	desired_state[1] = desired_item;
	desired_state[2] = desired_buff;
	desired_state[3] = desired_potion;

	// Use Bastille Battalion control rig
	string page = visit_url("inv_use.php?pwd&whichitem=9928");

	// Determine current state
	if (page.contains_text('barb1.png')) current_state[0] = 0; // Mys (Barbecue)
	else if (page.contains_text('barb2.png')) current_state[0] = 1; // Mus (Babar)
	else if (page.contains_text('barb3.png')) current_state[0] = 2; // Mox (Barbershop)

	if (page.contains_text('bridge1.png')) current_state[1] = 0; // Mus (Brutalist)
	else if (page.contains_text('bridge2.png')) current_state[1] = 1; // Mys (Draftsman)
	else if (page.contains_text('bridge3.png')) current_state[1] = 2; // Mox (Art Nouveau)

	if (page.contains_text('holes1.png')) current_state[2] = 0; // Mus (Cannon)
	else if (page.contains_text('holes2.png')) current_state[2] = 1; // Mys (Catapult)
	else if (page.contains_text('holes3.png')) current_state[2] = 2; // Mox (Gesture)

	if (page.contains_text('moat1.png')) current_state[3] = 0; // Military (Sharks)
	else if (page.contains_text('moat2.png')) current_state[3] = 1; // Castle (Lava)
	else if (page.contains_text('moat3.png')) current_state[3] = 2; // Psychological (Truth Serum)

	// Cycle through each reward type to get the correct one for us
	for (int reward = 0; reward < 4; reward++)
	{
		int clicks = ((desired_state[reward] - current_state[reward]) + 3) % 3;
		for (int i = 0; i < clicks; i++) run_choice(reward + 1);
	}

	// Start fighting
	run_choice(5);

	boolean fighting = true;

	while(fighting)
	{
		run_choice(3); // Cheese
		run_choice(1); // First cheese option
		run_choice(3); // More Cheese
		run_choice(1); // First cheese option
		string fight_result = run_choice(1); // Get the jump
		fighting = fight_result.contains_text("You have razed");
	}

	run_choice(1); // Lock in your score
}

void consult_d0rfl()
{
	if (!is_online("d0rfl"))
	{
		print("d0rfl not online", "red");
		return;
	}
	if (get_property("_clanFortuneConsultUses") == 3)
	{
		print("Already consulted", "red");
		return;
	}
	while ((is_online("d0rfl")) && (get_property("_clanFortuneConsultUses") < 3))
	{
		cli_execute("fortune d0rfl pizza batman thick");
		// Adjust delay as needed.
		waitq(15);
	}

}


void main(){
	// SETTINGS
	// Find a method for checking if one has a tea tree
	if (get_property("barf_treeshake") == "")
	{
		if (user_confirm("Shake Tree?"))
		{
			set_property("barf_treeshake" , true);
		}
		else
		{
			set_property("barf_treeshake" , false);
		}
	}

	//breakfast
	print("Running breakfast commands", "black");

    if (get_property("_pottedTeaTreeUsed") == "false")
	{
		if (get_property("barf_treeshake") == "true")
		{
			print("Shaking Tree!", "blue");
			cli_execute("teatree shake");
		else {
			print("Grabbing a cuppa", "blue");
			cli_execute("teatree royal");
		}
	}

	//Performs a check to see if you have horsery and if dark horse already in usage
    if ((get_property("_horsery") != "dark horse") && (get_property("horseryAvailable") == "true"))
	{
		print("Getting Horse", "blue");
		cli_execute("horsery dark");
	}
	// Implement NEP
	print("Obtaining stuffs from d0rfl", "blue");
	consult_d0rfl();
    //Set boombox to food for initial stuffs.
    //cli_execute("boombox food");

//
}
