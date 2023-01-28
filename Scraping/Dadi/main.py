from itertools import product


def get_matching_event(event_condition, generic_outcome_space):
    return set([outcome for outcome in generic_outcome_space if event_condition(outcome)])


def compute_event_probability(event_condition, generic_sample_space):
    samples = get_matching_event(event_condition, generic_sample_space)

    outcomes_num = 0
    for dices_sum in samples:
        outcomes_num += generic_sample_space[dices_sum]

    sample_space_num = 0
    for dices_sum in generic_sample_space:
        sample_space_num += generic_sample_space[dices_sum]

    return outcomes_num / sample_space_num


def has_sum_of_21(outcome): return outcome == 21

possible_rolls = list(range(1, 7))
sample_space_tuples = set(product(possible_rolls, repeat=6))

sample_space = dict()
for outcome in sample_space_tuples:
    key = sum(outcome)
    value = sample_space.get(key)

    if value == None:
        sample_space.update({key: 1})
    else:
        sample_space.update({key: value + 1})

prob = compute_event_probability(has_sum_of_21, sample_space)
print(f"6 rolls sum to 21 with a probability of {prob}")
