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

void create_pants()
{
	if (item_amount($item[portable pantogram]) == 0)
	{
		print("Missing pantogram pants!", "red");
		return;
	}
	print("Summoning thine pants", "blue");
	if (get_property("_pantogramModifier") != "")
	{
		print("Already used today!", "red");
		return;
	}
	if ((item_amount($item[porquoise]) == 0)  && (get_property("barf_purchase") == "false"))
	{
		print("No porquoise found!", "red");
		return;
	}
	// Checks if porquoise is below 30k, then attempts to purchase if so.
	else if ((item_amount($item[porquoise]) == 0) && (get_property("barf_purchase") == "true"))
	{
		if (mall_price($item[porquoise]) < 30000)
		{
			print("Purchasing porquoise.", "blue");
			buy(1, $item[porquoise], 30000);
			use(1, $item[disassembled clover]);
			visit_url("inv_use.php?pwd&which=99&whichitem=9573");
			visit_url("choice.php?whichchoice=1270&pwd&option=1&m=3&e=5&s1=5789%2C1&s2=706%2C1&s3=24%2C1", true, true);
			if (item_amount($item[pantogram pants]) == 0)
			{
				print("Something went wrong!", "red");
				// Forcably convert all ten-leaf clovers to disassembled.
				while (item_amount($item[ten-leaf clover]) != 0)
				{
					use(1, $item[ten-leaf clover]);
				}
			}
			return;
		}
	}
	else if (item_amount($item[disassembled clover]) == 0)
	{
		print("Why do you not have a clover? Get a clover and rerun.", "red");
		return;
	}
	// At this point assuming everything went well.
	else
	{
		use(1, $item[disassembled clover]);
		visit_url("inv_use.php?pwd&which=99&whichitem=9573");
		visit_url("choice.php?whichchoice=1270&pwd&option=1&m=3&e=5&s1=5789%2C1&s2=706%2C1&s3=24%2C1", true, true);
		if (item_amount($item[pantogram pants]) == 0)
		{
			print("Something went wrong!", "red");
			// Forcably convert all ten-leaf clovers to disassembled.
			while (item_amount($item[ten-leaf clover]) != 0)
			{
				use(1, $item[ten-leaf clover]);
			}
		}
		return;
	}
}

void fork_slider(int consume_amount)
{
	int iterations  = 0;
	while (iterations < consume_amount)
	{
		float hp_percentage = my_hp()/my_maxhp();
		if (hp_percentage < .60)
		{
			restore_hp(my_maxhp());
		}
		eat(1, $item[Ol' Scratch's salad fork]);
		eat(1, $item[extra-greasy slider]);
		// Self incrementing loop
		iterations = iterations + 1;
		}
}

void mug_juice(int consume_amount)
{
	int iterations = 0;
	while (iterations < consume_amount)
	{
		float hp_percentage = my_hp()/my_maxhp();
		if (hp_percentage < .60)
		{
			restore_hp(my_maxhp());
		}
		eat(1m $item[Frosty's frosty mug]);
		eat(1, $item[jar of fermented pickle juice]);
		iterations = iterations + 1;
	}
}

void raid_stash()
{
	// Under most situations, don't enable.
	if ((get_property("barf_raidstash") == "true") && (get_clan_name() == "Redemption City")
	{
		print("Grabbing buffers from the Clan Stash", "red");
		refresh_stash();
		item PYEC = $item[Platinum Yendorian Express Card];
		item Defective = $item[Defective Game Grid Token];
		if (stash_amount(Defective) > 0)
		{
			take_stash(1, Defective);
			use(1, Defective);
			put_stash(1, Defective);
		}
		if (stash_amount(PYEC) > 0)
		{
			take_stash(1, PYEC);
			use(1, PYEC);
			put_stash(1, PYEC);
		}
	}
	else
	{
		print("Ignoring clan stash items", "blue");
	}
}
void main()
{
	int start_meat = my_meat();
	// SETTINGS
	// DEFAULT: SET TO OBTAIN ROYAL TEA
	if (get_property("barf_treeoption") == "")
	{
		set_property("barf_treeoption", "royal");
	}
	// DEFAULT: SET TO NON PURCHASE
	if (get_property("barf_purchase") == "")
	{
		set_property("barf_purchase", false);
	}

	// DEFAULT: SET TO NOT GRAB STUFFS FROM STASH
	if (get_property("barf_raidstash") == "")
	{
		set_property("barf_raidstash", "false");
	}

	// CHECK FUNCTIONS FOR PERSONAL SETTING
	// Checks to see if you want to shake tree
	if ((get_property("barf_treeshake") == "") && (get_campground() contains $item[potted tea tree]))
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
	// Auto eat preferences
	if (get_property("barf_consume") == "")
	{
		if (user_confirm("Automatically consume a hobo diet?"))
		{
			set_property("barf_consume", true);
		}
		else
		{
			set_property("barf_consume", false);
		}
	}

	// BREAKFAST
	// Store initial meat count

	print("Running breakfast commands", "black");

    if ((get_property("_pottedTeaTreeUsed") == "false") && (get_campground() contains $item[potted tea tree]))
	{
		if (get_property("barf_treeshake") == "true")
		{
			print("Shaking Tree!", "blue");
			cli_execute("teatree shake");
		}
		// Picking tea
		else
		{
			if (get_property("barf_treeoption") == "voraci")
			{
				print("Picking Voraci tea", "blue");
				cli_execute("teatree voraci");
			}
			else if (get_property("barf_treeoption") == "sobrie")
			{
				print("Picking Sobrie tea", "blue");
				cli_execute("teatree sobrie");
			}
			else
			{
				print("Defaulting to royal tea", "blue");
				cli_execute("teatree royal");
			}
		}
	}

	// Performs a check to see if you have horsery and if dark horse already in usage
    if ((get_property("_horsery") != "dark horse") && (get_property("horseryAvailable") == "true"))
	{
		print("Getting Horse", "blue");
		cli_execute("horsery dark");
	}

	// Collect the Carpe
	if (item_amount($item[carpe]) == 0)
	{
		print("Obtaining Carpe", "blue");
		visit_url("clan_viplounge.php?preaction=buyfloundryitem&whichitem=9001");
	}
	// Summoning thine pants
	create_pants();

	// Implement NEP
	print("Obtaining stuffs from d0rfl", "blue");
	consult_d0rfl();
    // Set boombox to food for initial stuffs.
    // cli_execute("boombox food");

	// Reactivate if breakfast isn't being run for some reason
	// visit_url('shop.php?whichshop=hippy');

	// Assumes you have Ezandora's FarFuture
	if (item_amount($item[Time-spinner]) == 1){
		cli_execute("farfuture tea");
	}

	// DIET
	if (get_property("barf_consume") == "true")
	{
		print("Consuming diet!", "red");
		// Just so you don't synthesize your UMSBs
		int UMSB_count = item_amount($item[Ultra Mega Sour Ball]);
		if (UMSB_count > 0)
		{
			put_closet(UMSB_count, $item[Ultra Mega Sour Ball]);
		}
		// Synthesize 20 times.
		cli_execute("synthesize meat");
		cli_execute("repeat 14");
		// Get ready for forking!
		print("Switching outfits for forking", "blue");
		cli_execute("maximize hot res");

		// Fork and sliders. Check to see if can use got milk first.
		// CLI attempt to make a milk of magnesium
		cli_execute("make milk of magnesium");
		if ((item_amount($item[milk of magnesium]) < 1) && (get_property("barf_purchase") == "true"))
		{
			buy(1, $item[milk of magnesium], 1000);
		}
		if (item_amount($item[milk of magnesium]) < 1)
		{
			print("Something went wrong with getting a milk of mag", "red");
			abort("Please either enable autopurchase, or just get a milk of mag");
		}
		use(1, $item[milk of magnesium]);
		fork_slider(3);

		// Attempt to use mugs


	}

	// END OF BUFFS
	// Autoyanking stuff from the clan stash,
	raid_stash();

	print("Final Counts", "blue");
	print("Starting meat: " + start_meat);
	print("End meat: " + my_meat());
	int difference = my_meat()- start_meat;
	print("Total Meat Gain: " + difference, "red");

//
}
