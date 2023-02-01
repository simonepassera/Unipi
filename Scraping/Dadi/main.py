from collections import defaultdict
from itertools import product


def get_matching_event(event_condition, generic_sample_space):
    return set([sum_key for sum_key in generic_sample_space.keys() if event_condition(sum_key)])


def compute_event_probability(event_condition, generic_sample_space):
    event = get_matching_event(event_condition, generic_sample_space)
    event_size = sum(generic_sample_space[dices_sum] for dices_sum in event)

    sample_space_num = 0
    for dices_sum in generic_sample_space:
        sample_space_num += generic_sample_space[dices_sum]

    return event_size / sum(generic_sample_space.values())


def has_sum_of_21(dices_sum): return dices_sum == 21


possible_rolls = list(range(1, 7))
sample_space_tuples = set(product(possible_rolls, repeat=6))

weighted_sample_space = defaultdict(int)

for outcome in sample_space_tuples:
    total = sum(outcome)
    weighted_sample_space[total] += 1

prob = compute_event_probability(has_sum_of_21, weighted_sample_space)
print(f"6 rolls sum to 21 with a probability of {prob}")
