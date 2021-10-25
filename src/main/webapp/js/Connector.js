submitGetRequest = async (parameters, url) => {
    let params = objectToGetParameters(parameters);
    console.log('Sending parameters:\n' + params);
    let response = await fetchRequestHandler(url + "?" + params);
    return response.ok;
}

xmlHttpRequestHandler = (url) =>{
    const xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.send(null);
}

fetchRequestHandler = (url) =>{
    return fetch(url, {
        method: 'get',
    });
}

objectToGetParameters = (object) => {
    let result = "";
    for (let param in object) {
        result = result + "&" + param + "=" + object[param];
    }
    return result.slice(1, result.length);
}

submitPostRequest = async (data, url) => {
    console.log('Sending json:\n' + JSON.stringify(data))

    let response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        let json = await response.json();
        console.log(json);
        return json;
    } else {
        throw new httpError("Ошибка HTTP: " + response.status);
    }
}

