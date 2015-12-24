package com.grafixartist.gallery.vk.model;

/**
 * Created by Dmitriy on 10/12/2015.
 */
public class VkResponse {
        private Response response;

        public Response getResponse ()
        {
            return response;
        }

        public void setResponse (Response response)
        {
            this.response = response;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [response = "+response+"]";
        }
}
