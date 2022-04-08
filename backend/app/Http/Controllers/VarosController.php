<?php

namespace App\Http\Controllers;

use App\Http\Requests\VarosRequest;
use App\Models\Varos;
use Exception;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator as FacadesValidator;

class VarosController extends Controller
{
    public function index()
    {
        $varosok = Varos::all();
        return response()->json($varosok);
    }

    public function store(VarosRequest $request) {        
        $varos = new Varos($request->only(["nev", "orszag", "lakossag"]));
        $varos->save();
        return response()->json($varos);  
    }
}
