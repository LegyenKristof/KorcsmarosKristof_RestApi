<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Varos>
 */
class VarosFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition()
    {
        return [
            "nev" => $this->faker->city(),
            "orszag" => $this->faker->country(),
            "lakossag" => $this->faker->numberBetween(100000, 10000000)
        ];
    }
}
