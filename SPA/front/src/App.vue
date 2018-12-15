<style>

#app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}

h1,
h2 {
    font-weight: normal;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    display: inline-block;
    margin: 0 10px;
}

a {
    color: #42b983;
}

</style>

<template>

<div class="container">
    <div class="large-12 medium-12 small-12 cell">
        <label>File
            <input type="file" id="file" ref="file" accept="image/*" v-on:change="handleFileUpload()" />

        </label>

        <button v-on:click="submitFile()" v-bind:disabled="inWork">Submit</button>
        <select v-model="selected">
            <option disabled value="">Выберите один из вариантов</option>
            <option>White/Black filter</option>
            <option>Blur filter</option>
            <option>Negative filter</option>
        </select>
        <button v-on:click="processFile();runWebSocket()" v-bind:disabled="inWork">Process</button>
    </div>
    <progress class="progress" v-bind:value="progress" max="100">{{progress}}%</progress>
    <img v-bind:src="imagePreview" v-show="showPreview" />
</div>

</template>

<script>

import axios from 'axios'
export default {
    name: 'app',
    data() {
        return {
            file: '',
            showPreview: false,
            imagePreview: '',
            fileName: '',
            url: 'http://localhost:8000',
            urlws: 'ws://localhost:8000',
            progress: 0,
            selected: '',
            inWork: false,
            id: ''
        }
    },

    methods: {
        handleFileUpload() {
                /*
                  Set the local file variable to what the user has selected.
                */
                this.file = this.$refs.file.files[0];

                /*
                  Initialize a File Reader object
                */
                let reader = new FileReader();

                /*
                  Add an event listener to the reader that when the file
                  has been loaded, we flag the show preview as true and set the
                  image to be what was read from the reader.
                */
                reader.addEventListener("load", function() {
                    this.showPreview = true;
                    this.imagePreview = reader.result;
                }.bind(this), false);

                /*
                  Check to see if the file is not empty.
                */
                if (this.file) {
                    /*
                      Ensure the file is an image file.
                    */
                    if (/\.(jpe?g|png|gif)$/i.test(this.file.name)) {
                        /*
                          Fire the readAsDataURL method which will read the file in and
                          upon completion fire a 'load' event which we will listen to and
                          display the image in the preview.
                        */
                        reader.readAsDataURL(this.file);
                        this.fileName = this.file.name;

                    }
                }
            },
            submitFile() {
                let formData = new FormData();

                /*
                    Add the form data we need to submit
                */
                formData.append('file', this.file);

                /*
                  Make the request to the POST /single-file URL
                */
                var that = this;
                axios.post(this.url + '/file',
                        formData, {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        }
                    ).then(function(response) {
                        console.log(response.data);
                        that.id = response.data;

                    })
                    .catch(function(error) {
                        console.log(error);
                    });
            },
            processFile() {
              this.inWork = true;
                axios.get(this.url + '/file', {
                        params: {
                            filter: this.selected,
                            id: this.id
                        }
                    })
                    .then(response => {
                        console.log(response);
                        // this.imagePreview = "data:image/png;base64," + response.data;
                        this.imagePreview = response.data;
                        this.inWork = false;
                        console.log("got");
                    })
                    .catch((error) => (console.log(error)));
            },
            runWebSocket() {
                var ws = new WebSocket(this.urlws + '/chat');
                var that = this;
                ws.onopen = function() {
                    // Web Socket is connected, send data using send()
                    // ws.send('Message to send');
                    // alert("Message is sent...");
                    console.log("connection opened");
                };
                ws.onmessage = function(evt) {
                    that.progress = evt.data;
                    console.log(that.progress);
                    // alert("Message is received...");
                };
                ws.onclose = function() {
                    // websocket is closed.
                    alert("Connection is closed...");
                };
            }
    }

}

</script>
