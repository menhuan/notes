import pytest


def sum_value(n, m):
    return n + m


def test_sum_value():
    value = sum_value(1, 2)
    assert value == 3


@pytest.fixture
def init_value():
    return 10


def total_sum_value(init_valie):
    result = sum_value(init_valie, 1)
    assert result == 11
